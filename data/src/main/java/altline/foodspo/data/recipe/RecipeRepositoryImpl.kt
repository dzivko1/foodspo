package altline.foodspo.data.recipe

import altline.foodspo.data.recipe.mapper.RecipeMapper
import altline.foodspo.data.recipe.model.Recipe

internal class RecipeRepositoryImpl(
    private val apiDataSource: RecipeApiDataSource,
    private val firebaseDataSource: RecipeFirebaseDataSource,
    private val mapRecipes: RecipeMapper
) : RecipeRepository {
    
    override suspend fun getRandomRecipes(count: Int): List<Recipe> {
        return apiDataSource.getRandomRecipes(count).map(mapRecipes::invoke)
    }
}