package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.domain.recipe.GetRecipeDetailsUseCase
import altline.foodspo.error.onError
import altline.foodspo.ui.recipe.RecipeUiMapper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val recipeUiMapper: RecipeUiMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val recipeId: Long = savedStateHandle["recipeId"]!!
    
    var uiState by mutableStateOf(RecipeDetailsUiState())
        private set
    
    init {
        loadRecipeDetails()
    }
    
    fun loadRecipeDetails() {
        viewModelScope.launch {
            kotlin.runCatching {
                uiState = RecipeDetailsUiState(loading = true)
                getRecipeDetailsUseCase(recipeId)
            }.onError {
                uiState = uiState.copy(error = it, loading = false)
            }.onSuccess { recipe ->
                uiState = recipeUiMapper.toRecipeDetailsUi(recipe)
            }
        }
    }
}