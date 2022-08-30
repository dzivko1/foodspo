package altline.foodspo.data.recipe.model.network

import com.squareup.moshi.Json

internal data class InstructionResponse(
    @Json(name = "number") val number: Int,
    @Json(name = "step") val text: String
)

internal data class AnalyzedInstructionsResponse(
    @Json(name = "steps") val steps: List<InstructionResponse>
)

internal data class AnalyzeInstructionsResponse(
    @Json(name = "parsedInstructions") val parsedInstructions: List<AnalyzedInstructionsResponse>
)
