package altline.foodspo.data.recipe

import altline.foodspo.data.recipe.mapper.RecipeMapper
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RecipeRepositoryImpl(
    private val apiDataSource: RecipeApiDataSource,
    private val firebaseDataSource: RecipeFirebaseDataSource,
    private val mapRecipes: RecipeMapper
) : RecipeRepository {
    
    override suspend fun getRandomRecipes(): Flow<List<Recipe>> {
        return apiDataSource.getRandomRecipes().map(mapRecipes::invoke)
    }
}