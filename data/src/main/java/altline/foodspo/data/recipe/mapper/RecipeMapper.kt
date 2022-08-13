package altline.foodspo.data.recipe.mapper

import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.ingredient.mapper.IngredientMapper
import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.data.recipe.model.network.RecipeResponse
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
            creditsText = raw.creditsText,
            sourceUrl = raw.sourceUrl,
            spoonacularSourceUrl = raw.spoonacularSourceUrl,
            servings = raw.servings,
            readyInMinutes = raw.readyInMinutes,
            instructions = raw.instructions.firstOrNull()?.steps?.map {
                Instruction(it.number, it.text)
            } ?: emptyList(),
            summary = raw.summary ?: "",
            ingredients = raw.extendedIngredients.map(mapIngredients::invoke),
            additionTime = null,
            isSaved = false
        )
    }
}