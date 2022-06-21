package altline.foodspo.ui.explore

import altline.foodspo.ui.core.LocalNavController
import altline.foodspo.ui.core.component.RecipeCard
import altline.foodspo.ui.core.component.RecipeCardUi
import altline.foodspo.ui.explore.ExploreUiState.*
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(viewModel: ExploreViewModel = koinViewModel()) {
    val appNavController = LocalNavController.current
    
    when (val uiState = viewModel.uiState) {
        is Loading -> {}
        is Error -> {}
        is Content -> Content(
            recipes = uiState.recipes,
            onRecipeClick = { appNavController.navigateToRecipeDetails(it) },
            onAddToShoppingList = { viewModel.addIngredientsToShoppingList(it) },
            onToggleSave = { viewModel.toggleSaveRecipe(it) }
        )
    }
}

@Composable
private fun Content(
    recipes: List<RecipeCardUi>,
    onRecipeClick: (recipeId: Long) -> Unit,
    onAddToShoppingList: (recipeId: Long) -> Unit,
    onToggleSave: (recipeId: Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        items(recipes) { recipe ->
            RecipeCard(
                recipe = recipe,
                onRecipeClick = { onRecipeClick(recipe.id) },
                onAddToShoppingList = { onAddToShoppingList(recipe.id) },
                onToggleSave = { onToggleSave(recipe.id) }
            )
        }
    }
}


@Preview
@Composable
fun PreviewContent() {
    AppTheme {
        Content(
            listOf(
                RecipeCardUi(
                    id = 0,
                    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
                    image = PlaceholderImages.recipe,
                    author = "Maplewood Road",
                    isSaved = false
                ),
                RecipeCardUi(
                    id = 1,
                    title = "Spaghetti with Meatballs",
                    image = PlaceholderImages.recipe,
                    author = "Maplewood Road",
                    isSaved = true
                )
            ),
            onRecipeClick = {},
            onAddToShoppingList = {},
            onToggleSave = {}
        )
    }
}