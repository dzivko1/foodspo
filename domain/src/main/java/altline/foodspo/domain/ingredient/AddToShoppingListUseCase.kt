package altline.foodspo.domain.ingredient

import altline.foodspo.data.ingredient.IngredientRepository
import altline.foodspo.data.ingredient.model.ShoppingItem
import javax.inject.Inject

class AddToShoppingListUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    suspend operator fun invoke(recipeTitle: String?, item: ShoppingItem): String {
        return ingredientRepository.addToShoppingList(recipeTitle, item)
    }
}