package altline.foodspo.data.ingredient

import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.ingredient.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {

    suspend fun parseIngredients(rawIngredients: List<String>): List<Ingredient>

    fun getShoppingList(): Flow<Map<String?, List<ShoppingItem>>>

    suspend fun addToShoppingList(recipeTitle: String?, vararg items: ShoppingItem): List<String>

    suspend fun removeFromShoppingList(recipeTitle: String?, item: ShoppingItem)

    suspend fun editShoppingList(recipeTitle: String?, item: ShoppingItem)
}