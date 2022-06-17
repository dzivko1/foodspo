package altline.foodspo.ui.explore

import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.ui.explore.ExploreUiState.*
import androidx.compose.runtime.Composable
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
    recipes: List<Recipe>
) {

}