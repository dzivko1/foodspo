package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.error.AppException
import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.recipe.component.IngredientListItemUi

data class RecipeDetailsUiState(
    val loading: Boolean = false,
    val error: AppException? = null,
    val id: Long = 0,
    val image: ImageSrc = PlaceholderImages.recipe,
    val title: String = "",
    val author: String? = null,
    val creditsText: String? = null,
    val servings: Int? = null,
    val readyInMinutes: Int? = null,
    val ingredients: List<IngredientListItemUi> = emptyList(),
    val instructions: List<Instruction> = emptyList(),
    val summary: String? = null,
    val sourceUrl: String? = null,
    val spoonacularSourceUrl: String? = null
)