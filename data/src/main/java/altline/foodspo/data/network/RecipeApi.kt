package altline.foodspo.data.network

import altline.foodspo.data.recipe.model.network.RandomRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RecipeApi {
    
    @GET("recipes/random")
    suspend fun getRandomRecipes(@Query("number") count: Int): RandomRecipesResponse
}