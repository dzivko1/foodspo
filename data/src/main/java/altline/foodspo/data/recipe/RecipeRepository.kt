package altline.foodspo.data.recipe

import altline.foodspo.data.recipe.model.Recipe

interface RecipeRepository {
    
    suspend fun getRandomRecipes(count: Int): List<Recipe>
    
    suspend fun getRecipeDetails(recipeId: Long): Recipe
}