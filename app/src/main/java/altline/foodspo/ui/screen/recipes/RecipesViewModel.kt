package altline.foodspo.ui.screen.recipes

import altline.foodspo.data.RECIPE_PAGE_SIZE
import altline.foodspo.data.core.paging.FlowPagingSource
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.domain.recipe.GetMyRecipesUseCase
import altline.foodspo.domain.recipe.GetSavedRecipesUseCase
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.recipe.RecipeUiMapper
import altline.foodspo.ui.recipe.component.RecipeCardUi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getMyRecipesUseCase: GetMyRecipesUseCase,
    private val getSavedRecipesUseCase: GetSavedRecipesUseCase,
    private val recipeUiMapper: RecipeUiMapper
) : ViewModel() {
    
    var uiState by mutableStateOf(RecipesUiState())
        private set
    
    init {
        loadData()
    }
    
    fun loadData() {
        uiState = RecipesUiState(
            data = RecipesScreenUi(
                myRecipes = constructPagedFlow(
                    dataProvider = getMyRecipesUseCase::invoke
                ),
                savedRecipes = constructPagedFlow(
                    dataProvider = getSavedRecipesUseCase::invoke
                )
            )
        )
    }
    
    private fun constructPagedFlow(
        dataProvider: (loadTrigger: Flow<Pair<Int, Int>>) -> Flow<List<Recipe>>
    ): Flow<PagingData<RecipeCardUi>> {
        return Pager(
            PagingConfig(RECIPE_PAGE_SIZE),
            pagingSourceFactory = {
                FlowPagingSource(dataProvider, viewModelScope)
            }
        ).flow.map { pagingData ->
            pagingData.map { recipe ->
                recipeUiMapper.toRecipeCard(
                    recipe,
                    enableSaveChange = false,
                    onContentClick = { navigateToRecipeDetails(recipe.id) },
                    onAddToShoppingList = { addIngredientsToShoppingList(recipe.id) }
                )
            }
        }.cachedIn(viewModelScope)
    }
    
    private fun addIngredientsToShoppingList(recipeId: Long) {
        TODO("Not yet implemented")
    }
    
    private fun navigateToRecipeDetails(recipeId: Long) {
        uiState = uiState.copy(navEvent = NavigationEvent.RecipeDetails(recipeId))
    }
    
    fun onNavEventConsumed() {
        uiState = uiState.copy(navEvent = null)
    }
}