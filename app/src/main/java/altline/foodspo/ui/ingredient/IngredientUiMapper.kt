package altline.foodspo.ui.ingredient

import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.ingredient.model.ShoppingItem
import altline.foodspo.ui.ingredient.component.IngredientListItemUi
import altline.foodspo.ui.ingredient.component.ShoppingListItemUi
import altline.foodspo.ui.screen.shopping.ShoppingScreenUi
import javax.inject.Inject

class IngredientUiMapper @Inject constructor() {

    fun toShoppingScreenUi(
        raw: Map<String?, List<ShoppingItem>>,
        onAddItem: () -> Unit,
        onTextChange: (recipeTitle: String?, itemId: String, text: String) -> Unit,
        onCheckedChange: (recipeTitle: String?, itemId: String, checked: Boolean) -> Unit,
        onEditingChange: (recipeTitle: String?, itemId: String, editing: Boolean) -> Unit,
        onRemove: (recipeTitle: String?, itemId: String) -> Unit
    ) = ShoppingScreenUi(
        shoppingItems = raw.mapValues { (category, items) ->
            items.map { item ->
                toShoppingListItemUi(
                    item,
                    onTextChange = { text -> onTextChange(category, item.id, text) },
                    onCheckedChange = { checked -> onCheckedChange(category, item.id, checked) },
                    onEditingChange = { editing -> onEditingChange(category, item.id, editing) },
                    onRemove = { onRemove(category, item.id) }
                )
            }
        },
        onAddItem = onAddItem
    )

    fun toShoppingListItemUi(
        raw: ShoppingItem,
        onTextChange: (String) -> Unit,
        onCheckedChange: (Boolean) -> Unit,
        onEditingChange: (Boolean) -> Unit,
        onRemove: () -> Unit
    ) = ShoppingListItemUi(
        id = raw.id,
        text = raw.text,
        onTextChange = onTextChange,
        checked = raw.checked,
        onCheckedChange = onCheckedChange,
        editing = false,
        onEditingChange = onEditingChange,
        onRemove = onRemove
    )

    fun fromShoppingListItemUi(ui: ShoppingListItemUi): ShoppingItem {
        return ShoppingItem(
            id = ui.id,
            text = ui.text,
            checked = ui.checked
        )
    }

    fun toListItemUi(raw: Ingredient) = IngredientListItemUi(
        id = raw.id,
        name = raw.name,
        measure = raw.measure
    )
}
