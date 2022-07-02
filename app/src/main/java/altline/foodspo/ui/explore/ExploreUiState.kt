package altline.foodspo.ui.explore

import altline.foodspo.ui.core.component.RecipeCardUi
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class ExploreUiState(
    val recipes: Flow<PagingData<RecipeCardUi>>
)