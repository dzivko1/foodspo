package altline.foodspo.ui.screen.recipes

import altline.foodspo.data.RECIPE_PAGE_SIZE
import altline.foodspo.data.core.paging.FlowPagingSource
import altline.foodspo.data.core.paging.PagingAccessor
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.domain.recipe.GetMyRecipesUseCase
import altline.foodspo.domain.recipe.GetSavedRecipesUseCase
import altline.foodspo.ui.core.ViewModelBase
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.recipe.RecipeUiMapper
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.screen.mealPlanner.MealPlannerViewModel
import androidx.lifecycle.SavedStateHandle
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
    private val recipeUiMapper: RecipeUiMapper,
    savedStateHandle: SavedStateHandle
) : ViewModelBase<RecipesScreenUi>() {

    private val isPickMode: Boolean = savedStateHandle.get(IS_PICK_MODE_NAV_ARG)!!

    override fun loadData() {
        setUiData(
            RecipesScreenUi(
                myRecipes = constructPagedFlow(
                    getMyRecipesUseCase(viewModelScope)
                ),
                savedRecipes = constructPagedFlow(
                    getSavedRecipesUseCase(viewModelScope)
                ),
                isPickMode = isPickMode,
                onCreateRecipeClick = this::navigateToNewRecipe,
                onExploreRecipesClick = this::navigateToExplore
            )
        )
    }

    private fun constructPagedFlow(
        pagingAccessor: PagingAccessor<Recipe>
    ): Flow<PagingData<RecipeCardUi>> {
        return Pager(
            PagingConfig(RECIPE_PAGE_SIZE),
            pagingSourceFactory = {
                FlowPagingSource(pagingAccessor, RECIPE_PAGE_SIZE)
            }
        ).flow.map { pagingData ->
            pagingData.map { recipe ->
                recipeUiMapper.toRecipeCard(
                    recipe,
                    enableSaveChange = false,
                    onContentClick = { navigateToRecipeDetails(recipe.id) },
                    onAddToShoppingList = { addIngredientsToShoppingList(recipe.id) },
                    onPick = if (isPickMode) {
                        { sendPickResult(recipe.id) }
                    } else null
                )
            }
        }.cachedIn(viewModelScope)
    }

    override fun onFabClick() {
        navigateToNewRecipe()
    }

    private fun addIngredientsToShoppingList(recipeId: String) {
        TODO("Not yet implemented")
    }

    private fun navigateToNewRecipe() {
        navigateTo(NavigationEvent.RecipeEditor(null))
    }

    private fun navigateToExplore() {
        navigateTo(NavigationEvent.Explore)
    }

    private fun navigateToRecipeDetails(recipeId: String) {
        navigateTo(NavigationEvent.RecipeDetails(recipeId))
    }

    private fun sendPickResult(recipeId: String) {
        finishWithResult(MealPlannerViewModel.PICKED_RECIPE_ID_RESULT_KEY, recipeId)
    }

    companion object {
        const val IS_PICK_MODE_NAV_ARG = "isPickMode"
    }
}