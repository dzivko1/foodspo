package altline.foodspo.ui.core.mapper

import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.ui.core.component.RecipeCardUi
import altline.foodspo.ui.placeholder.PlaceholderImages

class RecipeUiMapper {
    
    fun toRecipeCard(recipe: Recipe) = RecipeCardUi(
        title = recipe.title,
        image = recipe.image ?: PlaceholderImages.recipe,
        author = recipe.sourceName,
        isSaved = recipe.isSaved
    )
    
    fun toRecipeCards(recipes: List<Recipe>) = recipes.map(::toRecipeCard)
}