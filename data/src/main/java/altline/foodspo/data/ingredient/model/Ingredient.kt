package altline.foodspo.data.ingredient.model

import altline.foodspo.data.core.model.ImageSrc

data class Ingredient(
    val id: String,
    val name: String,
    val image: ImageSrc?,
    val aisle: String,
    val amount: Double,
    val measure: Measure?,
    val rawText: String
)

internal data class IngredientFirestore(
    var id: String = "",
    var name: String = "",
    var image: Map<String, String> = emptyMap(),
    var aisle: String = "",
    var amount: Double = 0.0,
    var measure: MeasureFirestore? = null,
    var rawText: String = ""
) {
    fun toDomainModel() = Ingredient(
        id,
        name,
        image["path"]?.let { ImageSrc(it) },
        aisle,
        amount,
        measure?.toDomainModel(),
        rawText
    )
}