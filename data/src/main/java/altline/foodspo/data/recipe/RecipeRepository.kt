package altline.foodspo.data.recipe

import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    
    suspend fun getRandomRecipes(count: Int): List<Recipe>
    
    fun getRecipeDetails(recipeId: Long): Flow<Recipe>
}