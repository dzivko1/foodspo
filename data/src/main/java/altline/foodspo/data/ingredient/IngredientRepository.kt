package altline.foodspo.data.ingredient

import altline.foodspo.data.ingredient.model.Ingredient

interface IngredientRepository {

    suspend fun parseIngredients(rawIngredients: List<String>): List<Ingredient>
}