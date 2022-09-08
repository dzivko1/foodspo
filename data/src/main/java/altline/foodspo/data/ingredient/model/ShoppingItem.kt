package altline.foodspo.data.ingredient.model

data class ShoppingItem(
    val id: String,
    val text: String,
    val checked: Boolean
)

internal data class ShoppingItemFirestore(
    var id: String = "",
    var text: String = "",
    var checked: Boolean = false
) {
    fun toDomainModel() = ShoppingItem(
        id, text, checked
    )
}