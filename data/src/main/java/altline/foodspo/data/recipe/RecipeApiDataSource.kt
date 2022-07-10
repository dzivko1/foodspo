package altline.foodspo.data.recipe

import altline.foodspo.data.network.RecipeApi
import altline.foodspo.data.recipe.model.network.RecipeResponse
import javax.inject.Inject

internal class RecipeApiDataSource @Inject constructor(
    private val recipeApi: RecipeApi
) {
    suspend fun getRandomRecipes(count: Int): List<RecipeResponse> {
        return recipeApi.getRandomRecipes(count).recipes
    }
}