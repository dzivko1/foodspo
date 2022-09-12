package altline.foodspo.data.meal.model

import altline.foodspo.data.core.model.ImageSrc

data class Meal(
    val id: String,
    val recipeId: String,
    val recipeTitle: String,
    val recipeImage: ImageSrc
)
