package altline.foodspo.ui.screen.explore

import altline.foodspo.ui.core.ScreenBase
import altline.foodspo.ui.core.component.PagedListStatus
import altline.foodspo.ui.recipe.component.RecipeCard
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.theme.AppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ExploreScreenUi(
    val recipes: Flow<PagingData<RecipeCardUi>>,
) {
    companion object {
        val PREVIEW = ExploreScreenUi(
            recipes = flowOf(
                PagingData.from(
                    listOf(
                        RecipeCardUi.PREVIEW,
                        RecipeCardUi.PREVIEW,
                        RecipeCardUi.PREVIEW
                    )
                )
            )
        )
    }
}

@Composable
fun ExploreScreen(viewModel: ExploreViewModel = hiltViewModel()) {
    ScreenBase(viewModel) {
        Content(it)
    }
}

@Composable
private fun Content(
    data: ExploreScreenUi
) {
    val pagedRecipes = data.recipes.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        items(pagedRecipes) { recipe ->
            if (recipe != null) {
                RecipeCard(recipe)
            }
        }
        item {
            PagedListStatus(pagedRecipes)
        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    AppTheme {
        Content(ExploreScreenUi.PREVIEW)
    }
}