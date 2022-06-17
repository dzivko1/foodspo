package altline.foodspo.data.recipe

import altline.foodspo.data.network.RecipeApi
import altline.foodspo.data.recipe.model.network.RecipeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class RecipeApiDataSource(
    private val recipeApi: RecipeApi
) {
    suspend fun getRandomRecipes() : Flow<List<RecipeResponse>> {
        return flow {
            emit(
                recipeApi.getRandomRecipes(5).recipes
            )
        }
    }
}