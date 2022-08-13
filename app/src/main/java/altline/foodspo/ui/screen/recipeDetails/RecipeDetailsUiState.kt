package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.data.error.AppException

data class RecipeDetailsUiState(
    val loading: Boolean = false,
    val error: AppException? = null,
    val data: RecipeDetailsScreenUi? = null
)