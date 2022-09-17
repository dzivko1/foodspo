package altline.foodspo.data.network

import altline.foodspo.data.ingredient.model.network.IngredientResponse
import altline.foodspo.data.recipe.model.network.AnalyzeInstructionsResponse
import altline.foodspo.data.recipe.model.network.RandomRecipesResponse
import altline.foodspo.data.recipe.model.network.RecipeResponse
import altline.foodspo.data.recipe.model.network.SearchRecipesResponse
import retrofit2.http.*

internal interface RecipeApi {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("offset") offset: Int,
        @Query("number") amount: Int,
    ): SearchRecipesResponse

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") amount: Int
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

    @POST("recipes/analyzeInstructions")
    @FormUrlEncoded
    suspend fun analyzeInstructions(
        @Field("instructions") instructions: String
    ): AnalyzeInstructionsResponse

    @POST("recipes/parseIngredients")
    @FormUrlEncoded
    suspend fun parseIngredients(
        @Field("ingredientList") ingredientList: String,
        @Query("language") language: String = "en",
        @Query("includeNutrition") includeNutrition: Boolean = false
    ): List<IngredientResponse>
}