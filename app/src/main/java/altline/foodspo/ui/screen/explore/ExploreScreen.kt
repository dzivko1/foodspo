package altline.foodspo.ui.screen.explore

import altline.foodspo.ui.core.LocalNavController
import altline.foodspo.ui.core.component.InfoPanel
import altline.foodspo.ui.core.component.LoadingSpinner
import altline.foodspo.ui.recipe.component.RecipeCard
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.anyError
import altline.foodspo.util.isAnyLoading
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
    val navController = LocalNavController.current
    
    with(viewModel.uiState) {
        if (navEvent != null) {
            navEvent.navigate(navController)
            viewModel.onNavEventConsumed()
        }
        data?.let { Content(it) }
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
            recipe?.let {
                RecipeCard(recipe)
            }
        }
        item {
            pagedRecipes.loadState.anyError?.let {
                InfoPanel(it) {
                    pagedRecipes.retry()
                }
            }
            
            if (pagedRecipes.loadState.isAnyLoading) {
                LoadingSpinner()
            }
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