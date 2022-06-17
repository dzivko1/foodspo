package altline.foodspo.ui.explore

import altline.foodspo.data.onError
import altline.foodspo.domain.recipe.GetRandomRecipesUseCase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val getRandomRecipes: GetRandomRecipesUseCase
) : ViewModel() {
    
    var uiState by mutableStateOf<ExploreUiState>(ExploreUiState.Loading)
        private set
    
    init {
        viewModelScope.launch {
            getRandomRecipes().onStart {
                uiState = ExploreUiState.Loading
            }.onError { error ->
                uiState = ExploreUiState.Error(error)
            }.collect { data ->
                uiState = ExploreUiState.Content(data)
            }
        }
    }
}