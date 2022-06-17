package altline.foodspo.data.ingredient.mapper

import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.ingredient.model.network.IngredientResponse
import altline.foodspo.data.util.ImageSrc

internal class IngredientMapper(
    private val mapMeasures: MeasureMapper
) {
    
    operator fun invoke(raw: IngredientResponse): Ingredient {
        return Ingredient(
            id = raw.id,
            name = raw.name ?: "",
            image = raw.image?.let { ImageSrc(it) },
            aisle = raw.aisle ?: "",
            amount = raw.amount ?: 0.0,
            measure = raw.measures?.let { mapMeasures(it) },
            rawText = raw.original ?: ""
        )
    }
    
    operator fun invoke(rawList: List<IngredientResponse>): List<Ingredient> {
        return rawList.map(::invoke)
    }
    
}