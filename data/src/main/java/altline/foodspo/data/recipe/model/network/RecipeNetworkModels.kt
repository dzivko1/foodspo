package altline.foodspo.data.recipe.model.network

import altline.foodspo.data.ingredient.model.network.IngredientResponse
import com.squareup.moshi.Json

internal data class RandomRecipesResponse(
    @Json(name = "recipes") val recipes: List<RecipeResponse>
)

internal data class RecipeResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String?,
    @Json(name = "image") val image: String?,
    @Json(name = "author") val author: String?,
    @Json(name = "sourceName") val sourceName: String?,
    @Json(name = "sourceUrl") val sourceUrl: String?,
    @Json(name = "creditsText") val creditsText: String?,
    @Json(name = "servings") val servings: Int?,
    @Json(name = "readyInMinutes") val readyInMinutes: Int?,
    @Json(name = "instructions") val instructions: String?,
    @Json(name = "summary") val summary: String?,
    @Json(name = "extendedIngredients") val extendedIngredients: List<IngredientResponse>
)
