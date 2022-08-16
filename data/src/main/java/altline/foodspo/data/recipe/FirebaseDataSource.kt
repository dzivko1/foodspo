package altline.foodspo.data.recipe

import altline.foodspo.data.core.paging.paginate
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.data.user.model.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {
    private lateinit var user: User

    private val myRecipesCollection
        get() = db.collection("users/${user.uid}/ownRecipes")

    private val savedRecipesCollection
        get() = db.collection("users/${user.uid}/savedRecipes")

    fun setCurrentUser(user: User) {
        this.user = user
    }

    fun getMyRecipesPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Recipe>> {
        return myRecipesCollection
            .orderBy("additionTime")
            .paginate(loadTrigger)
            .map { docs -> docs.map { it.toObject()!! } }
    }

    fun getSavedRecipeIdsPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<String>> {
        return savedRecipesCollection
            .orderBy("additionTime")
            .paginate(loadTrigger)
            .map { docs -> docs.map { it.id } }
    }

    suspend fun isRecipeSaved(recipeId: String): Boolean {
        return savedRecipesCollection.document(recipeId)
            .get().await()
            .exists()
    }

    suspend fun saveRecipe(recipeId: String, save: Boolean) {
        if (save) {
            savedRecipesCollection.document(recipeId)
                .set(mapOf("additionTime" to Timestamp.now()))
                .await()
        } else {
            savedRecipesCollection.document(recipeId)
                .delete()
                .await()
        }
    }
}