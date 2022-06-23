package altline.foodspo.data.recipe

import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.network.RecipeApi
import altline.foodspo.data.recipe.model.network.RecipeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class RecipeApiDataSource(
    private val recipeApi: RecipeApi,
    private val mapExceptions: ExceptionMapper
) {
    suspend fun getRandomRecipes(): Flow<List<RecipeResponse>> {
        return flow {
            emit(
                mapExceptions {
                    recipeApi.getRandomRecipes(5).recipes
                }
            )
        }
    }
}