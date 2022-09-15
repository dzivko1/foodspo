package altline.foodspo.data.meal.model

import altline.foodspo.data.core.model.DrawableImageSrc
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.core.model.PathImageSrc

data class Meal(
    val id: String,
    val recipeId: String,
    val recipeTitle: String,
    val recipeImage: ImageSrc?
) {
    fun toFirestoreModel() = MealFirestore(
        id, recipeId, recipeTitle, mapOf(
            "path" to when (recipeImage) {
                is PathImageSrc -> recipeImage.path
                is DrawableImageSrc -> recipeImage.drawableRes
                else -> ""
            }
        )
    )
}

data class MealFirestore(
    var id: String = "",
    var recipeId: String = "",
    var recipeTitle: String = "",
    var recipeImage: Map<String, Any> = emptyMap()
) {
    fun toDomainModel() = Meal(
        id, recipeId, recipeTitle,
        recipeImage = recipeImage["path"]?.let {
            when (it) {
                is String -> ImageSrc(path = it)
                is Long -> ImageSrc(drawableRes = it.toInt())
                else -> null
            }
        }
    )
}
