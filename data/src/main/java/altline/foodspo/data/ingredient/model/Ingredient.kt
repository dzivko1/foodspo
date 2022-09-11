package altline.foodspo.data.ingredient.model

import altline.foodspo.data.core.model.ImageSrc

data class Ingredient(
    val id: String,
    val name: String,
    val image: ImageSrc?,
    val aisle: String,
    val measure: Measure?,
    val rawText: String
) {
    fun toShoppingItem(checked: Boolean = false): ShoppingItem {
        return ShoppingItem(
            id = id,
            text = rawText,
            checked = checked
        )
    }
}

internal data class IngredientFirestore(
    var id: String = "",
    var name: String = "",
    var image: Map<String, String>? = null,
    var aisle: String = "",
    var measure: MeasureFirestore? = null,
    var rawText: String = ""
) {
    fun toDomainModel() = Ingredient(
        id,
        name,
        image?.get("path")?.let { ImageSrc(it) },
        aisle,
        measure?.toDomainModel(),
        rawText
    )
}