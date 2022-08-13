package altline.foodspo.ui.screen.recipes

import altline.foodspo.data.error.AppException
import altline.foodspo.ui.core.navigation.NavigationEvent

data class RecipesUiState(
    val loading: Boolean = false,
    val error: AppException? = null,
    val navEvent: NavigationEvent? = null,
    val data: RecipesScreenUi? = null
)