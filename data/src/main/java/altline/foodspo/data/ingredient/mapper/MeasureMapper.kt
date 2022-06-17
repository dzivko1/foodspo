package altline.foodspo.data.ingredient.mapper

import altline.foodspo.data.ingredient.model.Measure
import altline.foodspo.data.ingredient.model.network.MeasureResponse
import altline.foodspo.data.ingredient.model.network.MeasuresResponse

internal class MeasureMapper {
    
    operator fun invoke(raw: MeasureResponse): Measure {
        return Measure(
            amount = raw.amount,
            unitShort = raw.unitShort,
            unitLong = raw.unitLong
        )
    }
    
    operator fun invoke(raw: MeasuresResponse): Measure? {
        val measureRaw = raw.metric ?: raw.us ?: return null
        return invoke(measureRaw)
    }
}