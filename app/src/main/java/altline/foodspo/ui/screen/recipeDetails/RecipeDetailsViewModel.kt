package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.domain.recipe.DeleteRecipeUseCase
import altline.foodspo.domain.recipe.GetRecipeDetailsUseCase
import altline.foodspo.domain.recipe.SaveRecipeUseCase
import altline.foodspo.ui.core.ViewModelBase
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.recipe.RecipeUiMapper
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val recipeUiMapper: RecipeUiMapper,
    savedStateHandle: SavedStateHandle
) : ViewModelBase<RecipeDetailsScreenUi>() {

    private val recipeId: String = savedStateHandle["recipeId"]!!

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch {
            runAction {
                getRecipeDetailsUseCase(recipeId)
            }.onSuccess { recipe ->
                setUiData(recipeUiMapper.toRecipeDetailsUi(recipe))
            }
        }
    }

    fun onAddToShoppingListClicked() {
        TODO("Not yet implemented")
    }

    fun onSaveClicked(save: Boolean) {
        viewModelScope.launch {
            runAction {
                saveRecipeUseCase(recipeId, save)
            }.onSuccess {
                setUiData(uiState.data?.copy(isSaved = save))
            }
        }
    }

    fun onEditClicked() {
        navigateTo(NavigationEvent.RecipeEditor(recipeId))
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            runAction {
                deleteRecipeUseCase(recipeId)
            }.onSuccess {
                navigateTo(NavigationEvent.Recipes)
            }
        }
    }
}