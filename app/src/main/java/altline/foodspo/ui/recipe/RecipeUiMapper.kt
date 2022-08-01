package altline.foodspo.ui.recipe

import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.screen.recipeDetails.RecipeDetailsUiState
import javax.inject.Inject

class RecipeUiMapper @Inject constructor(
    private val ingredientUiMapper: IngredientUiMapper
) {
    
    fun toRecipeCard(raw: Recipe) = RecipeCardUi(
        id = raw.id,
        title = raw.title,
        image = raw.image ?: PlaceholderImages.recipe,
        author = raw.sourceName,
        isSaved = raw.isSaved
    )
    
    fun toRecipeDetailsUi(raw: Recipe) = RecipeDetailsUiState(
        id = raw.id,
        image = raw.image ?: PlaceholderImages.recipe,
        title = raw.title,
        author = raw.sourceName,
        creditsText = raw.creditsText,
        servings = raw.servings,
        readyInMinutes = raw.readyInMinutes,
        ingredients = raw.ingredients.map(ingredientUiMapper::toListItemUi),
        instructions = raw.instructions,
        summary = raw.summary,
        sourceUrl = raw.sourceUrl,
        spoonacularSourceUrl = raw.spoonacularSourceUrl
    )
}