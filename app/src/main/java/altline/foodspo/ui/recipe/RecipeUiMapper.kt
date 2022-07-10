package altline.foodspo.ui.recipe

import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.recipe.component.RecipeCardUi
import javax.inject.Inject

class RecipeUiMapper @Inject constructor() {
    
    fun toRecipeCard(data: Recipe) = RecipeCardUi(
        id = data.id,
        title = data.title,
        image = data.image ?: PlaceholderImages.recipe,
        author = data.sourceName,
        isSaved = data.isSaved
    )
}