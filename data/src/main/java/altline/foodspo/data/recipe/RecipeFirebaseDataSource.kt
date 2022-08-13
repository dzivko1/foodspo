package altline.foodspo.data.recipe

import altline.foodspo.data.FirestoreSchema
import altline.foodspo.data.core.paging.paginate
import altline.foodspo.data.recipe.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RecipeFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {
    fun getMyRecipesPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Recipe>> {
        return db.collection(FirestoreSchema.Users.OwnRecipes.toString())
            .orderBy(FirestoreSchema.RecipeModel.additionTime)
            .paginate(loadTrigger)
            .map { docs -> docs.map { it.toObject<Recipe>()!! } }
        
    }
    
    fun getSavedRecipeIdsPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Long>> {
        return db.collection(FirestoreSchema.Users.SavedRecipes.toString())
            .orderBy(FirestoreSchema.RecipeModel.additionTime)
            .paginate(loadTrigger)
            .map { docs -> docs.map { it.toObject()!! } }
    }
}