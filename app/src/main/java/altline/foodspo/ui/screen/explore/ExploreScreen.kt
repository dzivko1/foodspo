package altline.foodspo.ui.screen.explore

import altline.foodspo.R
import altline.foodspo.ui.core.DefaultTopBar
import altline.foodspo.ui.core.ScreenBase
import altline.foodspo.ui.core.component.PagedListStatus
import altline.foodspo.ui.core.component.SearchBar
import altline.foodspo.ui.core.component.SearchBarUi
import altline.foodspo.ui.recipe.component.RecipeCard
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.OnKeyboardStateChange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ExploreScreenUi(
    val searchBarUi: SearchBarUi?,
    val recipes: Flow<PagingData<RecipeCardUi>>,
) {
    companion object {
        @Composable
        fun preview() = ExploreScreenUi(
            searchBarUi = SearchBarUi.preview(),
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
    ScreenBase(
        viewModel,
        topBar = {
            TopBar(
                searchBarUi = viewModel.uiState.data?.searchBarUi,
                onToggleSearch = viewModel::onToggleSearch,
            )
        }
    ) {
        Content(it)
    }
}

@Composable
private fun TopBar(
    searchBarUi: SearchBarUi?,
    onToggleSearch: () -> Unit,
) {
    if (searchBarUi != null) {
        TopAppBar {
            SearchBar(searchBarUi)
        }
        OnKeyboardStateChange { state ->
            if (!state) onToggleSearch()
        }
    } else {
        DefaultTopBar(
            actions = {
                IconButton(onClick = onToggleSearch) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = stringResource(R.string.content_desc_search_recipes)
                    )
                }
            }
        )
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
        Content(ExploreScreenUi.preview())
    }
}