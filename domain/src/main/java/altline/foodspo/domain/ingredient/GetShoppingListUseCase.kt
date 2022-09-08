package altline.foodspo.domain.ingredient

import altline.foodspo.data.ingredient.IngredientRepository
import altline.foodspo.data.ingredient.model.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingListUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    operator fun invoke(): Flow<Map<String?, List<ShoppingItem>>> {
        return ingredientRepository.getShoppingList()
    }
}