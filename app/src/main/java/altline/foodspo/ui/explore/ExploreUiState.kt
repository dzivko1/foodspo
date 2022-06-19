package altline.foodspo.ui.explore

import altline.foodspo.data.ErrorInfo
import altline.foodspo.ui.core.component.RecipeCardUi

sealed interface ExploreUiState {
    object Loading : ExploreUiState
    data class Error(val error: ErrorInfo) : ExploreUiState
    data class Content(val recipes: List<RecipeCardUi>) : ExploreUiState
}