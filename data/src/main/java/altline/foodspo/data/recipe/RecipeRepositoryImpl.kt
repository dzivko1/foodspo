package altline.foodspo.data.recipe

import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.recipe.mapper.RecipeMapper
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class RecipeRepositoryImpl @Inject constructor(
    private val apiDataSource: RecipeApiDataSource,
    private val firebaseDataSource: RecipeFirebaseDataSource,
    private val mapRecipe: RecipeMapper,
    private val mapExceptions: ExceptionMapper
) : RecipeRepository {
    
    override suspend fun getRandomRecipes(count: Int): List<Recipe> {
        return mapExceptions {
            apiDataSource.getRandomRecipes(count).map(mapRecipe::invoke)
        }
    }
    
    override fun getRecipeDetails(recipeId: Long): Flow<Recipe> {
        return flow {
            emit(
                mapExceptions {
                    val response = apiDataSource.getRecipeDetails(recipeId)
                    mapRecipe(response)
                }
            )
        }
    }
}