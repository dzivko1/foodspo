package altline.foodspo.data.recipe.model

import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.util.ImageSrc

data class Recipe(
    val id: Long,
    val title: String,
    val image: ImageSrc?,
    val author: String,
    val sourceName: String,
    val sourceUrl: String,
    val creditsText: String,
    val servings: Int?,
    val readyInMinutes: Int?,
    val instructions: String,
    val summary: String,
    val ingredients: List<Ingredient>
)
