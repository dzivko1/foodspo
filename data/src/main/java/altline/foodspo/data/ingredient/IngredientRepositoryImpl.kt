package altline.foodspo.data.ingredient

import altline.foodspo.data.core.FirebaseDataSource
import altline.foodspo.data.core.RecipeApiDataSource
import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.ingredient.mapper.IngredientMapper
import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.ingredient.model.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class IngredientRepositoryImpl @Inject constructor(
    private val apiDataSource: RecipeApiDataSource,
    private val firebaseDataSource: FirebaseDataSource,
    private val mapIngredient: IngredientMapper,
    private val mapException: ExceptionMapper
) : IngredientRepository {

    override suspend fun parseIngredients(rawIngredients: List<String>): List<Ingredient> {
        return mapException {
            rawIngredients.filter { it.isNotBlank() }.run {
                if (isNotEmpty())
                    apiDataSource.parseIngredients(rawIngredients)
                        .map(mapIngredient::invoke)
                else emptyList()
            }
        }
    }

    override fun getShoppingList(): Flow<Map<String?, List<ShoppingItem>>> {
        return mapException.forFlow(
            firebaseDataSource.getShoppingList()
        )
    }

    override suspend fun addToShoppingList(recipeTitle: String?, item: ShoppingItem): String {
        return mapException {
            firebaseDataSource.addToShoppingList(recipeTitle, item)
        }
    }

    override suspend fun removeFromShoppingList(recipeTitle: String?, item: ShoppingItem) {
        mapException {
            firebaseDataSource.removeFromShoppingList(recipeTitle, item)
        }
    }

    override suspend fun editShoppingList(recipeTitle: String?, item: ShoppingItem) {
        mapException {
            firebaseDataSource.editShoppingList(recipeTitle, item)
        }
    }
}