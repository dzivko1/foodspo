package altline.foodspo.data.ingredient

import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.ingredient.mapper.IngredientMapper
import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.recipe.RecipeApiDataSource
import javax.inject.Inject

internal class IngredientRepositoryImpl @Inject constructor(
    private val apiDataSource: RecipeApiDataSource,
    private val mapIngredient: IngredientMapper,
    private val mapException: ExceptionMapper
) : IngredientRepository {

    override suspend fun parseIngredients(rawIngredients: List<String>): List<Ingredient> {
        return mapException {
            if (rawIngredients.isNotEmpty()) {
                apiDataSource.parseIngredients(rawIngredients).map(mapIngredient::invoke)
            } else emptyList()
        }
    }
}