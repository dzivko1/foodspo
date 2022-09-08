package altline.foodspo.data.ingredient.model.network

import altline.foodspo.data.ingredient.model.ShoppingItem
import altline.foodspo.data.ingredient.model.ShoppingItemFirestore

internal data class ShoppingListNetwork(
    val items: List<ShoppingItem>
)

internal data class ShoppingListFirestore(
    var items: List<ShoppingItemFirestore> = emptyList()
)