package altline.foodspo.data.core

import altline.foodspo.data.CUSTOM_RECIPE_ID_PREFIX
import altline.foodspo.data.SHOPPING_ITEM_ID_PREFIX
import altline.foodspo.data.UNCATEGORIZED_SHOPPING_LIST_ID
import altline.foodspo.data.core.model.BitmapImageSrc
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.core.model.PathImageSrc
import altline.foodspo.data.core.paging.PageLoadTrigger
import altline.foodspo.data.core.paging.paginate
import altline.foodspo.data.ingredient.model.ShoppingItem
import altline.foodspo.data.ingredient.model.network.ShoppingListFirestore
import altline.foodspo.data.ingredient.model.network.ShoppingListNetwork
import altline.foodspo.data.meal.model.MealPlan
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.data.recipe.model.RecipeFirestore
import altline.foodspo.data.user.model.User
import altline.foodspo.data.util.asSnapshotFlow
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    @ApplicationContext private val context: Context
) {
    private lateinit var user: User

    private val myRecipesCollection
        get() = getCollectionForUser("ownRecipes")

    private val savedRecipesCollection
        get() = getCollectionForUser("savedRecipes")

    private val shoppingListCollection
        get() = getCollectionForUser("shoppingList")

    private val mealPlanCollection
        get() = getCollectionForUser("mealPlan")

    private val recipeImageStore
        get() = storage.getReference("users/${user.uid}/recipeImages")

    private fun getCollectionForUser(collectionPath: String): CollectionReference {
        return db.collection("users/${user.uid}/$collectionPath")
    }

    fun setCurrentUser(user: User) {
        this.user = user
    }

    fun getMyRecipesPaged(loadTrigger: PageLoadTrigger): Flow<List<Recipe>> {
        return myRecipesCollection
            .orderBy(ADDITION_TIME_FIELD)
            .paginate(loadTrigger)
            .map { docs ->
                docs.map { it.toObject<RecipeFirestore>()!!.toDomainModel() }
            }
    }

    suspend fun getMyRecipeDetails(recipeId: String): Recipe? {
        return myRecipesCollection
            .document(recipeId)
            .get().await()
            .toObject<RecipeFirestore>()?.toDomainModel()
    }

    fun getSavedRecipeIdsPaged(loadTrigger: PageLoadTrigger): Flow<List<String>> {
        return savedRecipesCollection
            .orderBy(ADDITION_TIME_FIELD)
            .paginate(loadTrigger)
            .map { docs -> docs.map { it.id } }
    }

    suspend fun isRecipeSaved(recipeId: String): Boolean {
        return savedRecipesCollection.document(recipeId)
            .get().await()
            .exists()
    }

    suspend fun storeCustomRecipe(recipe: Recipe): String {
        val recipeId =
            if (recipe.isOwnedByUser) recipe.id
            else CUSTOM_RECIPE_ID_PREFIX + UUID.randomUUID()

        val modelToStore = if (recipe.image is BitmapImageSrc || recipe.image is PathImageSrc) {
            val firebaseFileUri = storeRecipeImage(recipeId, recipe.image)
            recipe.copy(
                id = recipeId,
                additionTime = Timestamp.now(),
                image = firebaseFileUri?.let { ImageSrc(it.toString()) } ?: recipe.image
            )
        } else {
            recipe.copy(
                id = recipeId,
                additionTime = Timestamp.now()
            )
        }

        myRecipesCollection.document(recipeId)
            .set(modelToStore).await()

        return recipeId
    }

    private suspend fun storeRecipeImage(recipeId: String, imageSrc: ImageSrc): Uri? {
        val fileRef = recipeImageStore.child(recipeId)
        return when (imageSrc) {
            is BitmapImageSrc -> {
                ByteArrayOutputStream().use { baos ->
                    imageSrc.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val imageData = baos.toByteArray()
                    fileRef.putBytes(imageData).await()
                    fileRef.downloadUrl.await()
                }
            }
            is PathImageSrc -> {
                if (!imageSrc.path.startsWith("http")) {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openInputStream(
                            imageSrc.path.toUri()
                        )?.use {
                            it.buffered().readBytes()
                        }?.let {
                            fileRef.putBytes(it).await()
                            fileRef.downloadUrl.await()
                        }
                    }
                } else null
            }
            else -> null
        }
    }

    suspend fun deleteRecipe(recipeId: String) {
        require(recipeId.startsWith(CUSTOM_RECIPE_ID_PREFIX)) { "Only custom recipes can be deleted" }
        val docDeleteTask = myRecipesCollection.document(recipeId).delete()
        val imgDeleteTask = recipeImageStore.child(recipeId).delete()
        docDeleteTask.await()
        imgDeleteTask.await()
    }

    suspend fun saveRecipe(recipeId: String, save: Boolean) {
        if (save) {
            savedRecipesCollection.document(recipeId)
                .set(mapOf(ADDITION_TIME_FIELD to Timestamp.now()))
                .await()
        } else {
            savedRecipesCollection.document(recipeId)
                .delete()
                .await()
        }
    }


    fun getShoppingList(): Flow<Map<String?, List<ShoppingItem>>> {
        return shoppingListCollection.asSnapshotFlow().map { s ->
            s.associate { snapshot ->
                val key = if (snapshot.id == UNCATEGORIZED_SHOPPING_LIST_ID) null else snapshot.id
                key to snapshot.toObject<ShoppingListFirestore>()
                    .items.map { it.toDomainModel() }
            }
        }.map {
            it.filter { (_, value) -> value.isNotEmpty() }
        }
    }

    suspend fun addToShoppingList(recipeTitle: String?, vararg items: ShoppingItem): List<String> {
        val toStore = items.map { it.copy(id = SHOPPING_ITEM_ID_PREFIX + UUID.randomUUID()) }
        shoppingListCollection.document(recipeTitle ?: UNCATEGORIZED_SHOPPING_LIST_ID)
            .set(
                mapOf(ITEMS_FIELD to FieldValue.arrayUnion(*toStore.toTypedArray())),
                SetOptions.merge()
            )
            .await()
        return toStore.map { it.id }
    }

    suspend fun removeFromShoppingList(recipeTitle: String?, item: ShoppingItem) {
        shoppingListCollection.document(recipeTitle ?: UNCATEGORIZED_SHOPPING_LIST_ID)
            .set(mapOf(ITEMS_FIELD to FieldValue.arrayRemove(item)), SetOptions.merge())
            .await()
    }

    suspend fun editShoppingList(recipeTitle: String?, item: ShoppingItem) {
        val docRef = shoppingListCollection.document(recipeTitle ?: UNCATEGORIZED_SHOPPING_LIST_ID)
        val items = docRef.get().await()
            .toObject<ShoppingListFirestore>()!!
            .items.map { it.toDomainModel() }
            .toMutableList()

        val index = items.indexOfFirst { it.id == item.id }
        items[index] = item
        docRef.set(ShoppingListNetwork(items))
    }


    fun getMealPlan(weekTimestamp: Timestamp): Flow<MealPlan?> {
        return mealPlanCollection.document(weekTimestamp.toString())
            .asSnapshotFlow()
            .map { it.toObject() }
    }

    companion object {
        private const val ADDITION_TIME_FIELD = "additionTime"
        private const val ITEMS_FIELD = "items"
    }
}