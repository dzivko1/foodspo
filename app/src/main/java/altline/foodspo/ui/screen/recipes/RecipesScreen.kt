package altline.foodspo.ui.screen.recipes

import altline.foodspo.R
import altline.foodspo.ui.core.ScreenBase
import altline.foodspo.ui.core.component.InfoPanel
import altline.foodspo.ui.core.component.PagedListStatus
import altline.foodspo.ui.recipe.component.RecipeCard
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    val onCreateRecipeClick: () -> Unit,
    val onExploreRecipesClick: () -> Unit
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
            ),
            onCreateRecipeClick = {},
            onExploreRecipesClick = {}
        )
    }
}

@Composable
fun RecipesScreen(viewModel: RecipesViewModel = hiltViewModel()) {
    ScreenBase(
        viewModel,
        fab = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.fab_new_recipe)) },
                onClick = viewModel::onFabClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) }
            )
        },
        reloadOnResume = true
    ) {
        Content(it)
    }
}

@Composable
private fun Content(
    data: RecipesScreenUi
) {
    val myRecipesPaged = data.myRecipes.collectAsLazyPagingItems()
    val savedRecipesPaged = data.savedRecipes.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        item {
            Text(
                text = stringResource(R.string.recipes_my_recipes_title),
                Modifier.padding(AppTheme.spaces.large),
                color = modifiedColor(alpha = ContentAlpha.medium),
                style = AppTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        }
        items(myRecipesPaged) { recipe ->
            if (recipe != null) {
                RecipeCard(recipe)
            }
        }
        item {
            PagedListStatus(
                items = myRecipesPaged,
                emptyContent = {
                    InfoPanel(
                        message = stringResource(R.string.recipes_my_recipes_empty_message),
                        actionLabel = stringResource(R.string.recipes_my_recipes_empty_create_button),
                        action = data.onCreateRecipeClick
                    )
                }
            )
        }

        item {
            Text(
                text = stringResource(R.string.recipes_saved_recipes_title),
                Modifier.padding(AppTheme.spaces.large),
                color = modifiedColor(alpha = ContentAlpha.medium),
                style = AppTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        }
        items(savedRecipesPaged) { recipe ->
            if (recipe != null) {
                RecipeCard(recipe)
            }
        }
        item {
            PagedListStatus(
                items = savedRecipesPaged,
                emptyContent = {
                    InfoPanel(
                        message = stringResource(R.string.recipes_saved_recipes_empty_message),
                        actionLabel = stringResource(R.string.recipes_saved_recipes_explore_button),
                        action = data.onExploreRecipesClick
                    )
                }
            )
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