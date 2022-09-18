package altline.foodspo.ui.screen.recipeEditor

import altline.foodspo.domain.ingredient.ParseIngredientsUseCase
import altline.foodspo.domain.recipe.AnalyzeInstructionsUseCase
import altline.foodspo.domain.recipe.StoreCustomRecipeUseCase
import altline.foodspo.domain.recipe.GetRecipeDetailsUseCase
import altline.foodspo.ui.core.ViewModelBase
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.recipe.RecipeUiMapper
import altline.foodspo.data.util.minusAt
import altline.foodspo.data.util.replaceAt
import altline.foodspo.error.onError
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeEditorViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val storeCustomRecipeUseCase: StoreCustomRecipeUseCase,
    private val analyzeInstructionsUseCase: AnalyzeInstructionsUseCase,
    private val parseIngredientsUseCase: ParseIngredientsUseCase,
    private val recipeUiMapper: RecipeUiMapper,
    savedStateHandle: SavedStateHandle
) : ViewModelBase<RecipeEditorScreenUi>() {

    private val recipeId: String = savedStateHandle[RECIPE_ID_NAV_ARG]!!

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch {
            runAction {
                val recipe =
                    if (recipeId != "null") getRecipeDetailsUseCase(recipeId)
                    else null
                setUiData(
                    recipeUiMapper.toRecipeEditorUi(
                        recipe,
                        onTitleChange = { setUiData(uiState.data?.copy(title = it)) },
                        onNewImage = { setUiData(uiState.data?.copy(image = it)) },
                        onServingsChange = { setUiData(uiState.data?.copy(servings = it)) },
                        onReadyInMinutesChange = { setUiData(uiState.data?.copy(readyInMinutes = it)) },
                        onInstructionsChange = { setUiData(uiState.data?.copy(instructions = it)) },
                        onSummaryChange = { setUiData(uiState.data?.copy(summary = it)) },
                        onEditingIngredientChange = this@RecipeEditorViewModel::setEditedIngredient,
                        onIngredientEdit = this@RecipeEditorViewModel::editIngredient,
                        onIngredientAdd = this@RecipeEditorViewModel::createIngredient,
                        onIngredientRemove = this@RecipeEditorViewModel::removeIngredient,
                        onSaveRecipe = this@RecipeEditorViewModel::saveRecipe
                    )
                )
            }.onError {
                showErrorScreen(it, retryAction = ::loadData)
            }
        }
    }

    private fun setEditedIngredient(index: Int, editing: Boolean) {
        setUiData(
            uiState.data?.copy(
                ingredientEditorUi = uiState.data!!.ingredientEditorUi.copy(
                    editingItemIndex = if (editing) index else null
                )
            )
        )
    }

    private fun editIngredient(index: Int, text: String) {
        setIngredients(uiState.data!!.ingredientEditorUi.items.replaceAt(index, text))
    }

    private fun createIngredient() {
        setUiData(
            uiState.data?.copy(
                ingredientEditorUi = uiState.data!!.ingredientEditorUi.copy(
                    items = uiState.data!!.ingredientEditorUi.items.plus(""),
                    editingItemIndex = uiState.data!!.ingredientEditorUi.items.size
                )
            )
        )
    }

    private fun removeIngredient(index: Int) {
        setIngredients(uiState.data!!.ingredientEditorUi.items.minusAt(index))
    }

    private fun saveRecipe() {
        val uiData = uiState.data!!
        if (uiData.title.isNotEmpty()) {
            viewModelScope.launch {
                runAction {
                    val parsedInstructions = analyzeInstructionsUseCase(uiData.instructions)
                    val parsedIngredients = parseIngredientsUseCase(uiData.ingredientEditorUi.items)
                    val recipe = recipeUiMapper.fromRecipeEditorUi(
                        uiData,
                        parsedInstructions,
                        parsedIngredients
                    )
                    storeCustomRecipeUseCase(recipe)
                }.onSuccess { storedRecipeId ->
                    navigateTo(NavigationEvent.RecipeDetails(storedRecipeId).pop(2))
                }
            }
        }
    }

    private fun setIngredients(ingredients: List<String>) {
        setUiData(
            uiState.data?.copy(
                ingredientEditorUi = uiState.data!!.ingredientEditorUi.copy(
                    items = ingredients
                )
            )
        )
    }

    companion object {
        const val RECIPE_ID_NAV_ARG = "recipeId"
    }
}