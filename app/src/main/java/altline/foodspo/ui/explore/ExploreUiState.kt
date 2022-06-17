package altline.foodspo.ui.explore

import altline.foodspo.data.ErrorInfo
import altline.foodspo.data.recipe.model.Recipe

sealed interface ExploreUiState {
    object Loading : ExploreUiState
    data class Error(val error: ErrorInfo) : ExploreUiState
    data class Content(val recipes: List<Recipe>) : ExploreUiState
}