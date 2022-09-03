package altline.foodspo.ui.ingredient

import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.ui.ingredient.component.IngredientListItemUi
import javax.inject.Inject

class IngredientUiMapper @Inject constructor() {

    fun toListItemUi(raw: Ingredient) = IngredientListItemUi(
        id = raw.id,
        name = raw.name,
        measure = raw.measure
    )
}