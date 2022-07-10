package altline.foodspo.data.recipe.mapper

import altline.foodspo.data.ingredient.mapper.IngredientMapper
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.data.recipe.model.network.RecipeResponse
import altline.foodspo.data.core.model.ImageSrc
import javax.inject.Inject

internal class RecipeMapper @Inject constructor(
    private val mapIngredients: IngredientMapper
) {
    operator fun invoke(raw: RecipeResponse): Recipe {
        return Recipe(
            id = raw.id,
            title = raw.title ?: "",
            image = raw.image?.let { ImageSrc(it) },
            sourceName = raw.sourceName,
            sourceUrl = raw.sourceUrl,
            creditsText = raw.creditsText,
            servings = raw.servings,
            readyInMinutes = raw.readyInMinutes,
            instructions = raw.instructions ?: "",
            summary = raw.summary ?: "",
            ingredients = mapIngredients(raw.extendedIngredients),
            isSaved = false
        )
    }
    
    operator fun invoke(rawList: List<RecipeResponse>): List<Recipe> {
        return rawList.map(::invoke)
    }
}