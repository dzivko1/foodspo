package altline.foodspo.ui.screen.explore

import altline.foodspo.data.RECIPE_PAGE_SIZE
import altline.foodspo.data.core.paging.FlowPagingSource
import altline.foodspo.data.core.paging.PagingAccessor
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.domain.recipe.GetRandomRecipesUseCase
import altline.foodspo.domain.recipe.SaveRecipeUseCase
import altline.foodspo.ui.core.ViewModelBase
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
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val recipeUiMapper: RecipeUiMapper
) : ViewModelBase<ExploreScreenUi>() {

    private lateinit var pagingSource: FlowPagingSource<Recipe>

    init {
        loadData()
    }

    override fun loadData() {
        setUiData(
            ExploreScreenUi(
                recipes = constructPagedFlow(
                    getRandomRecipesUseCase(viewModelScope)
                )
            )
        )
    }

    private fun constructPagedFlow(
        pagingAccessor: PagingAccessor<Recipe>
    ): Flow<PagingData<RecipeCardUi>> {
        return Pager(
            PagingConfig(RECIPE_PAGE_SIZE),
            pagingSourceFactory = {
                FlowPagingSource(pagingAccessor, RECIPE_PAGE_SIZE).also { pagingSource = it }
            }
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

    private fun saveRecipe(recipeId: String, save: Boolean) {
        viewModelScope.launch {
            runAction {
                saveRecipeUseCase(recipeId, save)
            }.onSuccess {
                pagingSource.invalidate()
            }
        }
    }

    private fun addIngredientsToShoppingList(recipeId: String) {
        TODO("Not yet implemented")
    }

    private fun navigateToRecipeDetails(recipeId: String) {
        navigateTo(NavigationEvent.RecipeDetails(recipeId))
    }
}