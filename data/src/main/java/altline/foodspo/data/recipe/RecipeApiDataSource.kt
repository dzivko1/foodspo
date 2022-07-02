package altline.foodspo.data.recipe

import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.network.RecipeApi
import altline.foodspo.data.recipe.model.network.RecipeResponse

internal class RecipeApiDataSource(
    private val recipeApi: RecipeApi,
    private val mapExceptions: ExceptionMapper
) {
    suspend fun getRandomRecipes(count: Int): List<RecipeResponse> {
        return mapExceptions {
            recipeApi.getRandomRecipes(count).recipes
        }
    }
}