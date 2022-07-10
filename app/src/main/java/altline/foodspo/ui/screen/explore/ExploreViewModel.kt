package altline.foodspo.ui.screen.explore

import altline.foodspo.data.RECIPE_PAGE_SIZE
import altline.foodspo.data.recipe.RecipePagingSource
import altline.foodspo.domain.recipe.GetRandomRecipesUseCase
import altline.foodspo.ui.recipe.RecipeUiMapper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getRandomRecipes: GetRandomRecipesUseCase,
    private val recipeUiMapper: RecipeUiMapper
) : ViewModel() {
    
    var uiState by mutableStateOf(ExploreUiState(emptyFlow()))
        private set
    
    init {
        loadRandomRecipes()
    }
    
    fun loadRandomRecipes() {
        viewModelScope.launch {
            uiState = ExploreUiState(
                Pager(
                    PagingConfig(RECIPE_PAGE_SIZE),
                    pagingSourceFactory = {
                        RecipePagingSource(
                            recipeProvider = { _, loadSize -> getRandomRecipes(loadSize) }
                        )
                    }
                ).flow.map { pagingData ->
                    pagingData.map { recipeUiMapper.toRecipeCard(it) }
                }.cachedIn(viewModelScope)
            )
        }
    }
    
    fun toggleSaveRecipe(recipeId: Long) {
    
    }
    
    fun addIngredientsToShoppingList(recipeId: Long) {
    
    }
}