package altline.foodspo.data.recipe.model

import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.ingredient.model.Ingredient

data class Recipe(
    val id: Long,
    val title: String,
    val image: ImageSrc?,
    val sourceName: String?,
    val creditsText: String?,
    val sourceUrl: String?,
    val spoonacularSourceUrl: String?,
    val servings: Int?,
    val readyInMinutes: Int?,
    val instructions: List<Instruction>,
    val summary: String?,
    val ingredients: List<Ingredient>,
    val isSaved: Boolean
)

data class Instruction(
    val number: Int,
    val text: String
)