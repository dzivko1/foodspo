package altline.foodspo.domain.ingredient

import altline.foodspo.data.recipe.RecipeRepository
import javax.inject.Inject

class AddRecipeToShoppingListUseCase @Inject constructor(
    private val addToShoppingListUseCase: AddToShoppingListUseCase,
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: String) {
        recipeRepository.getRecipeDetails(recipeId)?.let { recipe ->
            addToShoppingListUseCase(
                recipe.title,
                *recipe.ingredients.map { it.toShoppingItem() }.toTypedArray()
            )
        }
    }
}