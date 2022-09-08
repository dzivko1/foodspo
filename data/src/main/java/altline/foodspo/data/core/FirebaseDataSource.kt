package altline.foodspo.data.core

import altline.foodspo.data.CUSTOM_RECIPE_ID_PREFIX
import altline.foodspo.data.SHOPPING_ITEM_ID_PREFIX
import altline.foodspo.data.UNCATEGORIZED_SHOPPING_LIST_ID
import altline.foodspo.data.core.model.BitmapImageSrc
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.core.paging.PageLoadTrigger
import altline.foodspo.data.core.paging.paginate
import altline.foodspo.data.ingredient.model.ShoppingItem
import altline.foodspo.data.ingredient.model.network.ShoppingListFirestore
import altline.foodspo.data.ingredient.model.network.ShoppingListNetwork
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.data.recipe.model.RecipeFirestore
import altline.foodspo.data.user.model.User
import altline.foodspo.data.util.asSnapshotFlow
import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    private lateinit var user: User

    private val myRecipesCollection
        get() = db.collection("users/${user.uid}/ownRecipes")

    private val savedRecipesCollection
        get() = db.collection("users/${user.uid}/savedRecipes")

    private val shoppingListCollection
        get() = db.collection("users/${user.uid}/shoppingList")

    private val recipeImageStore
        get() = storage.getReference("users/${user.uid}/recipeImages")

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

    suspend fun getMyRecipeDetails(recipeId: String): Recipe {
        return myRecipesCollection
            .document(recipeId)
            .get().await()
            .toObject<RecipeFirestore>()!!.toDomainModel()
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

        val modelToStore = if (recipe.image is BitmapImageSrc) {
            val fileUri = storeRecipeImage(recipeId, recipe.image.bitmap)
            recipe.copy(
                id = recipeId,
                additionTime = Timestamp.now(),
                image = ImageSrc(fileUri.toString())
            )
        } else {
            recipe.copy(
                id = recipeId,
                additionTime = Timestamp.now()
            )
        }

        myRecipesCollection.document(recipeId)
            .set(modelToStore)

        return recipeId
    }

    private suspend fun storeRecipeImage(recipeId: String, bitmap: Bitmap): Uri {
        var imageData: ByteArray
        ByteArrayOutputStream().use { baos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            imageData = baos.toByteArray()
        }

        val fileRef = recipeImageStore.child(recipeId)
        fileRef.putBytes(imageData).await()

        return fileRef.downloadUrl.await()
    }

    suspend fun deleteRecipe(recipeId: String) {
        require(recipeId.startsWith(CUSTOM_RECIPE_ID_PREFIX)) { "Only custom recipes can be deleted" }
        myRecipesCollection.document(recipeId)
            .delete()
            .await()
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

    suspend fun addToShoppingList(recipeTitle: String?, item: ShoppingItem): String {
        val toStore = item.copy(id = SHOPPING_ITEM_ID_PREFIX + UUID.randomUUID())
        shoppingListCollection.document(recipeTitle ?: UNCATEGORIZED_SHOPPING_LIST_ID)
            .set(mapOf(ITEMS_FIELD to FieldValue.arrayUnion(toStore)), SetOptions.merge())
            .await()
        return toStore.id
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

    companion object {
        private const val ADDITION_TIME_FIELD = "additionTime"
        private const val ITEMS_FIELD = "items"
    }
}