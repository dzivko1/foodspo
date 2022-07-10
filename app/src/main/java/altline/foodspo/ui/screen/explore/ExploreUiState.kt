package altline.foodspo.ui.screen.explore

import altline.foodspo.ui.recipe.component.RecipeCardUi
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class ExploreUiState(
    val recipes: Flow<PagingData<RecipeCardUi>>
)