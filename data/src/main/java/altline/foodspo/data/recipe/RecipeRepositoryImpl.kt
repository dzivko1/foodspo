package altline.foodspo.data.recipe

import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.recipe.mapper.RecipeMapper
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RecipeRepositoryImpl @Inject constructor(
    private val apiDataSource: RecipeApiDataSource,
    private val firebaseDataSource: RecipeFirebaseDataSource,
    private val mapRecipe: RecipeMapper,
    private val mapException: ExceptionMapper
) : RecipeRepository {
    
    override suspend fun getRandomRecipes(count: Int): List<Recipe> {
        return mapException {
            apiDataSource.getRandomRecipes(count).map(mapRecipe::invoke)
        }
    }
    
    override suspend fun getRecipeDetails(recipeId: String): Recipe {
        return mapException {
            mapRecipe(
                apiDataSource.getRecipeDetails(recipeId)
            )
        }
    }
    
    override suspend fun getRecipeDetailsBulk(recipeIds: List<String>): List<Recipe> {
        return mapException {
            apiDataSource.getRecipeDetailsBulk(recipeIds).map(mapRecipe::invoke)
        }
    }
    
    override fun getMyRecipesPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Recipe>> {
        return mapException.forFlow(
            firebaseDataSource.getMyRecipesPaged(loadTrigger)
        )
    }
    
    override fun getSavedRecipesPaged(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Recipe>> {
        return mapException.forFlow(
            firebaseDataSource.getSavedRecipeIdsPaged(loadTrigger)
                .map(this::getRecipeDetailsBulk)
        )
    }
}