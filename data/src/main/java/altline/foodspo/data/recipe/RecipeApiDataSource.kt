package altline.foodspo.data.recipe

import altline.foodspo.data.core.paging.PageLoadTrigger
import altline.foodspo.data.core.paging.pagedFlow
import altline.foodspo.data.network.RecipeApi
import altline.foodspo.data.recipe.model.network.RecipeResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RecipeApiDataSource @Inject constructor(
    private val recipeApi: RecipeApi
) {
    fun getRandomRecipesPaged(loadTrigger: PageLoadTrigger): Flow<List<RecipeResponse>> {
        return pagedFlow(loadTrigger) { loadParams, _ ->
            recipeApi.getRandomRecipes(loadParams.pageSize).recipes
        }
    }

    suspend fun getRecipeDetails(recipeId: String): RecipeResponse {
        return recipeApi.getRecipeInformation(recipeId)
    }

    suspend fun getRecipeDetailsBulk(recipeIds: List<String>): List<RecipeResponse> {
        return recipeApi.getRecipeInformationBulk(recipeIds.joinToString(","))
    }
}