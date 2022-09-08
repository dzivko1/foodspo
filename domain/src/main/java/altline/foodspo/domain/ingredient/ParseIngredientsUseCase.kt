package altline.foodspo.domain.ingredient

import altline.foodspo.data.ingredient.IngredientRepository
import altline.foodspo.data.ingredient.model.Ingredient
import javax.inject.Inject

class ParseIngredientsUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    suspend operator fun invoke(rawIngredients: List<String>): List<Ingredient> {
        return ingredientRepository.parseIngredients(rawIngredients)
    }

    suspend operator fun invoke(rawIngredient: String): Ingredient {
        return ingredientRepository.parseIngredients(listOf(rawIngredient)).first()
    }
}