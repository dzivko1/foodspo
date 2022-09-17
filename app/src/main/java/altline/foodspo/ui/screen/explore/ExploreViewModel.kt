package altline.foodspo.ui.screen.explore

import altline.foodspo.data.RECIPE_PAGE_SIZE
import altline.foodspo.data.core.paging.FlowPagingSource
import altline.foodspo.data.core.paging.IndexedPagingSource
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.domain.ingredient.AddRecipeToShoppingListUseCase
import altline.foodspo.domain.recipe.GetRandomRecipesUseCase
import altline.foodspo.domain.recipe.SaveRecipeUseCase
import altline.foodspo.domain.recipe.SearchRecipesUseCase
import altline.foodspo.ui.core.ViewModelBase
import altline.foodspo.ui.core.component.SearchBarUi
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.recipe.RecipeUiMapper
import altline.foodspo.ui.recipe.component.RecipeCardUi
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val addRecipeToShoppingListUseCase: AddRecipeToShoppingListUseCase,
    private val recipeUiMapper: RecipeUiMapper
) : ViewModelBase<ExploreScreenUi>() {

    private lateinit var activePagingSource: PagingSource<Int, Recipe>

    init {
        loadData()
    }

    override fun loadData() {
        setUiData(
            ExploreScreenUi(
                searchBarUi = null,
                recipes = randomRecipesPagedFlow()
            )
        )
    }

    private fun randomRecipesPagedFlow(): Flow<PagingData<RecipeCardUi>> {
        val pagingAccessor = getRandomRecipesUseCase(viewModelScope)
        return constructPagedFlow {
            FlowPagingSource(
                pagingAccessor,
                RECIPE_PAGE_SIZE
            ).also { activePagingSource = it }
        }
    }

    private fun recipeSearchPagedFlow(query: String): Flow<PagingData<RecipeCardUi>> {
        return constructPagedFlow {
            IndexedPagingSource(
                RECIPE_PAGE_SIZE,
                dataProvider = { page, loadSize -> searchRecipesUseCase(query, page, loadSize) }
            ).also { activePagingSource = it }
        }
    }

    private fun constructPagedFlow(
        pagingSourceFactory: () -> PagingSource<Int, Recipe>
    ): Flow<PagingData<RecipeCardUi>> {
        return Pager(
            PagingConfig(RECIPE_PAGE_SIZE),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { recipe ->
                recipeUiMapper.toRecipeCard(
                    recipe,
                    enableSaveChange = true,
                    onContentClick = { navigateToRecipeDetails(recipe.id) },
                    onAddToShoppingList = { addIngredientsToShoppingList(recipe.id) },
                    onSavedChange = { saved -> saveRecipe(recipe.id, saved) }
                )
            }
        }.cachedIn(viewModelScope)
    }

    private fun updateSearchQuery(query: String) {
        setUiData(
            uiState.data?.copy(
                searchBarUi = uiState.data!!.searchBarUi?.copy(
                    query = query
                )
            )
        )
    }

    private fun performRecipeSearch(query: String) {
        setUiData(
            uiState.data?.copy(
                recipes = recipeSearchPagedFlow(query)
            )
        )
    }

    private fun saveRecipe(recipeId: String, save: Boolean) {
        viewModelScope.launch {
            runAction {
                saveRecipeUseCase(recipeId, save)
            }.onSuccess {
                activePagingSource.invalidate()
            }
        }
    }

    private fun addIngredientsToShoppingList(recipeId: String) {
        viewModelScope.launch {
            runAction {
                addRecipeToShoppingListUseCase(recipeId)
            }
        }
    }

    private fun navigateToRecipeDetails(recipeId: String) {
        navigateTo(NavigationEvent.RecipeDetails(recipeId))
    }

    fun onToggleSearch() {
        setUiData(
            uiState.data?.copy(
                searchBarUi =
                if (uiState.data?.searchBarUi == null) {
                    SearchBarUi(
                        query = "",
                        onQueryChange = this::updateSearchQuery,
                        onSearch = this::performRecipeSearch
                    )
                } else null
            )
        )
    }
}