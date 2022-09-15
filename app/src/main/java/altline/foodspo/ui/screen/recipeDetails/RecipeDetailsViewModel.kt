package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.domain.ingredient.AddRecipeToShoppingListUseCase
import altline.foodspo.domain.ingredient.AddToShoppingListUseCase
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
    private val addRecipeToShoppingListUseCase: AddRecipeToShoppingListUseCase,
    private val addToShoppingListUseCase: AddToShoppingListUseCase,
    private val recipeUiMapper: RecipeUiMapper,
    savedStateHandle: SavedStateHandle
) : ViewModelBase<RecipeDetailsScreenUi>() {

    private val recipeId: String = savedStateHandle[RECIPE_ID_NAV_ARG]!!

    private var recipe: Recipe? = null

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch {
            runAction {
                recipe = getRecipeDetailsUseCase(recipeId)
                setUiData(recipe?.let { recipe ->
                    recipeUiMapper.toRecipeDetailsUi(
                        recipe,
                        onAddIngredientToShoppingList = this@RecipeDetailsViewModel::addIngredientToShoppingList
                    )
                })
            }
        }
    }

    private fun addIngredientToShoppingList(ingredientId: String) {
        viewModelScope.launch {
            runAction {
                val item = recipe?.ingredients?.find { it.id == ingredientId }?.toShoppingItem()
                if (item != null) {
                    addToShoppingListUseCase(recipe!!.title, item)
                }
            }
        }
    }

    fun onAddRecipeToShoppingListClicked() {
        viewModelScope.launch {
            runAction {
                addRecipeToShoppingListUseCase(recipeId)
            }
        }
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

    companion object {
        const val RECIPE_ID_NAV_ARG = "recipeId"
    }
}