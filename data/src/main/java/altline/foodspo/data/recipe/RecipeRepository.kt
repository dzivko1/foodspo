package altline.foodspo.data.recipe

import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    
    suspend fun getRandomRecipes(count: Int): List<Recipe>
    
    suspend fun getRecipeDetails(recipeId: Long): Recipe
    
    suspend fun getRecipeDetailsBulk(recipeIds: List<Long>): List<Recipe>
    
    fun getMyRecipesPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Recipe>>
    
    fun getSavedRecipesPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Recipe>>
}