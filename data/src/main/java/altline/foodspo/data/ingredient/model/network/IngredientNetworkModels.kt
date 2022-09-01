package altline.foodspo.data.ingredient.model.network

import com.squareup.moshi.Json

internal data class IngredientResponse(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String?,
    @Json(name = "image") val image: String?,
    @Json(name = "aisle") val aisle: String?,
    @Json(name = "measures") val measures: MeasuresResponse?,
    // amount, unitShort and unitLong are elements of MeasureResponse and are used in situations
    // where measure data is merged with ingredient data
    @Json(name = "amount") val amount: Double?,
    @Json(name = "unitShort") val unitShort: String?,
    @Json(name = "unitLong") val unitLong: String?,
    @Json(name = "original") val original: String?
)