package altline.foodspo.data.network

import altline.foodspo.data.recipe.model.network.RandomRecipesResponse
import altline.foodspo.data.recipe.model.network.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RecipeApi {
    
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") count: Int
    ): RandomRecipesResponse
    
    @GET("recipes/{recipeId}/information")
    suspend fun getRecipeInformation(
        @Path("recipeId") recipeId: String,
        @Query("includeNutrition") includeNutrition: Boolean = false
    ): RecipeResponse
    
    @GET("recipes/informationBulk")
    suspend fun getRecipeInformationBulk(
        @Query("ids") ids: String,
        @Query("includeNutrition") includeNutrition: Boolean = false
    ): List<RecipeResponse>
}