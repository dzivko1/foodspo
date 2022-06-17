package altline.foodspo.data.ingredient.model.network

import com.squareup.moshi.Json

internal data class IngredientResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "image") val image: String?,
    @Json(name = "aisle") val aisle: String?,
    @Json(name = "amount") val amount: Double?,
    @Json(name = "measures") val measures: MeasuresResponse?,
    @Json(name = "original") val original: String?
)