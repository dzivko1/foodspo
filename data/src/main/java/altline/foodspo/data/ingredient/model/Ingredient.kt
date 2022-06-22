package altline.foodspo.data.ingredient.model

import altline.foodspo.data.core.model.ImageSrc

data class Ingredient(
    val id: Long,
    val name: String,
    val image: ImageSrc?,
    val aisle: String,
    val amount: Double,
    val measure: Measure?,
    val rawText: String
)