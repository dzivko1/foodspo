package altline.foodspo.data.recipe.model.network

import altline.foodspo.data.ingredient.model.network.IngredientResponse
import com.squareup.moshi.Json

internal data class RandomRecipesResponse(
    @Json(name = "recipes") val recipes: List<RecipeResponse>
)

internal data class RecipeResponse(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String?,
    @Json(name = "image") val image: String?,
    @Json(name = "sourceName") val sourceName: String?,
    @Json(name = "creditsText") val creditsText: String?,
    @Json(name = "sourceUrl") val sourceUrl: String?,
    @Json(name = "spoonacularSourceUrl") val spoonacularSourceUrl: String?,
    @Json(name = "servings") val servings: Int?,
    @Json(name = "readyInMinutes") val readyInMinutes: Int?,
    @Json(name = "analyzedInstructions") val instructions: List<AnalyzedInstructionsResponse>,
    @Json(name = "summary") val summary: String?,
    @Json(name = "extendedIngredients") val extendedIngredients: List<IngredientResponse>
)

internal data class AnalyzedInstructionsResponse(
    @Json(name = "steps") val steps: List<InstructionResponse>
)

internal data class InstructionResponse(
    @Json(name = "number") val number: Int,
    @Json(name = "step") val text: String
)
