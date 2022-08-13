package altline.foodspo.ui.screen.recipes

import altline.foodspo.ui.core.LocalNavController
import altline.foodspo.ui.core.component.InfoPanel
import altline.foodspo.ui.core.component.LoadingSpinner
import altline.foodspo.ui.core.component.PageLoadingIndicator
import altline.foodspo.ui.recipe.component.RecipeCard
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.anyError
import altline.foodspo.util.isAnyLoading
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class RecipesScreenUi(
    val myRecipes: Flow<PagingData<RecipeCardUi>>,
    val savedRecipes: Flow<PagingData<RecipeCardUi>>,
) {
    companion object {
        val PREVIEW = RecipesScreenUi(
            myRecipes = flowOf(
                PagingData.from(
                    listOf(
                        RecipeCardUi.PREVIEW,
                        RecipeCardUi.PREVIEW
                    )
                )
            ),
            savedRecipes = flowOf(
                PagingData.from(
                    listOf(
                        RecipeCardUi.PREVIEW,
                        RecipeCardUi.PREVIEW
                    )
                )
            )
        )
    }
}

@Composable
fun RecipesScreen(viewModel: RecipesViewModel = hiltViewModel()) {
    val navController = LocalNavController.current
    
    with(viewModel.uiState) {
        if (navEvent != null) {
            navEvent.navigate(navController)
            viewModel.onNavEventConsumed()
        }
        if (loading) PageLoadingIndicator()
        
        if (error != null) InfoPanel(error, retryAction = viewModel::loadData)
        else if (data != null) Content(data)
    }
}

@Composable
private fun Content(
    data: RecipesScreenUi
) {
    val myRecipesPaged = data.myRecipes.collectAsLazyPagingItems()
    
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        items(myRecipesPaged) { recipe ->
            recipe?.let {
                RecipeCard(recipe)
            }
        }
        item {
            myRecipesPaged.loadState.anyError?.let {
                InfoPanel(it) {
                    myRecipesPaged.retry()
                }
            }
            
            if (myRecipesPaged.loadState.isAnyLoading) {
                LoadingSpinner()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    AppTheme {
        Surface {
            Content(RecipesScreenUi.PREVIEW)
        }
    }
}