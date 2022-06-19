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
    
    val uiState = viewModel.uiState
    
    when (uiState) {
        is Content -> Content(uiState.recipes)
        is Loading -> {}
        is Error -> {}
    }
}

@Composable
private fun Content(
    recipes: List<RecipeCardUi>
) {
    val appNavController = LocalNavController.current
    
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        items(recipes) { recipe ->
            RecipeCard(
                recipe = recipe,
                onRecipeClick = { appNavController.navigateToRecipeDetails(recipe.id) },
                onAddToShoppingList = {},
                onSaveToggle = {}
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
            )
        )
    }
}