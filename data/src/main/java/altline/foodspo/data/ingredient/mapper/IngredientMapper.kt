package altline.foodspo.data.ingredient.mapper

import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.ingredient.model.Measure
import altline.foodspo.data.ingredient.model.network.IngredientResponse
import javax.inject.Inject

internal class IngredientMapper @Inject constructor(
    private val mapMeasures: MeasureMapper
) {

    operator fun invoke(raw: IngredientResponse): Ingredient {
        return Ingredient(
            id = raw.id,
            name = raw.name ?: "",
            image = raw.image?.let { ImageSrc(it) },
            aisle = raw.aisle ?: "",
            measure = raw.measures?.let { mapMeasures(it) }
                ?: if (raw.amount != null && raw.unitShort != null && raw.unitLong != null) {
                    Measure(
                        raw.amount, raw.unitShort,
                        raw.unitLong
                    )
                } else null,
            amount = raw.amount ?: 0.0,
            rawText = raw.original ?: ""
        )
    }
}