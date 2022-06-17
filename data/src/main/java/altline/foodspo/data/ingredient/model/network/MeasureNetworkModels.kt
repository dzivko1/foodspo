package altline.foodspo.data.ingredient.model.network

import com.squareup.moshi.Json

internal data class MeasuresResponse(
    @Json(name = "us") val us: MeasureResponse?,
    @Json(name = "metric") val metric: MeasureResponse?
)

internal data class MeasureResponse(
    @Json(name = "amount") val amount: Double,
    @Json(name = "unitShort") val unitShort: String,
    @Json(name = "unitLong") val unitLong: String
)