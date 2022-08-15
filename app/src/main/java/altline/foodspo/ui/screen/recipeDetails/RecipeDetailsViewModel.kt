package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.domain.recipe.GetRecipeDetailsUseCase
import altline.foodspo.error.onError
import altline.foodspo.ui.core.ViewModelBase
import altline.foodspo.ui.recipe.RecipeUiMapper
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val recipeUiMapper: RecipeUiMapper,
    savedStateHandle: SavedStateHandle
) : ViewModelBase<RecipeDetailsScreenUi>() {

    private val recipeId: String = savedStateHandle["recipeId"]!!

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launchLoading {
            kotlin.runCatching {
                getRecipeDetailsUseCase(recipeId)
            }.onError {
                setError(it)
            }.onSuccess { recipe ->
                uiState = uiState.copy(
                    data = recipeUiMapper.toRecipeDetailsUi(recipe)
                )
            }
        }
    }
}