package altline.foodspo.ui.recipe

import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.screen.recipeDetails.RecipeDetailsScreenUi
import javax.inject.Inject

class RecipeUiMapper @Inject constructor(
    private val ingredientUiMapper: IngredientUiMapper
) {
    
    fun toRecipeCard(
        raw: Recipe,
        enableSaveChange: Boolean,
        onContentClick: () -> Unit,
        onAddToShoppingList: () -> Unit,
        onSavedChange: (Boolean) -> Unit = {}
    ) = RecipeCardUi(
        id = raw.id,
        title = raw.title,
        image = raw.image ?: PlaceholderImages.recipe,
        author = raw.sourceName,
        isSaved = if (enableSaveChange) raw.isSaved else null,
        onContentClick = onContentClick,
        onAddToShoppingList = onAddToShoppingList,
        onSavedChange = onSavedChange
    )
    
    fun toRecipeDetailsUi(raw: Recipe) = RecipeDetailsScreenUi(
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