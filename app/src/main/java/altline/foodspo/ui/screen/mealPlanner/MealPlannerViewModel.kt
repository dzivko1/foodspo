package altline.foodspo.ui.screen.mealPlanner

import altline.foodspo.data.WEEK_PAGE_SIZE
import altline.foodspo.data.core.paging.IndexedPagingSource
import altline.foodspo.data.util.*
import altline.foodspo.domain.meal.AddMealToPlanUseCase
import altline.foodspo.domain.meal.GetMealPlanUseCase
import altline.foodspo.ui.core.ViewModelBase
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.meal.MealUiMapper
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlannerViewModel @Inject constructor(
    private val getMealPlanUseCase: GetMealPlanUseCase,
    private val addMealToPlanUseCase: AddMealToPlanUseCase,
    private val mealUiMapper: MealUiMapper
) : ViewModelBase<MealPlannerScreenUi>() {

    private var selectedWeek: Timestamp? = null
    private var selectedWeekListenerJob: Job? = null
    private var pickingForDay: Timestamp? = null

    init {
        loadData()
    }

    override fun loadData() {
        selectedWeek = null
        selectedWeekListenerJob?.cancel()
        selectedWeekListenerJob = null
        pickingForDay = null

        setUiData(
            MealPlannerScreenUi(
                selectedWeekPlan = null,
                weekTimestamps = weekTimestampPagedFlow(),
                onWeekClick = this::selectWeek,
                onBackFromWeekPlan = this::goToWeekPicker
            )
        )
    }

    private fun weekTimestampPagedFlow(): Flow<PagingData<Timestamp>> {
        return Pager(
            PagingConfig(WEEK_PAGE_SIZE),
            pagingSourceFactory = {
                IndexedPagingSource(
                    WEEK_PAGE_SIZE,
                    dataProvider = { page, loadSize ->
                        val start = Timestamp.now().toLocalDate().atStartOfWeek()
                            .plusWeeks((page * WEEK_PAGE_SIZE).toLong())

                        buildList<Timestamp> {
                            for (i in 0 until loadSize) {
                                add(start.plusWeeks(i.toLong()).toTimestamp())
                            }
                        }
                    },
                    allowNegativePages = true
                )
            }
        ).flow
    }

    private fun selectWeek(timestamp: Timestamp) {
        setLoading(true)
        pickingForDay = null
        selectedWeekListenerJob?.cancel()
        selectedWeekListenerJob = viewModelScope.launch {
            getMealPlanUseCase(timestamp).collect { mealPlan ->
                setLoading(false)
                selectedWeek = mealPlan.weekTimestamp

                setUiData(
                    mealUiMapper.toMealPlannerScreenUi(
                        mealPlan,
                        onWeekClicked = this@MealPlannerViewModel::selectWeek,
                        onAddMealClicked = this@MealPlannerViewModel::addMeal,
                        onNextWeekClicked = this@MealPlannerViewModel::goToNextWeek,
                        onPrevWeekClicked = this@MealPlannerViewModel::goToPrevWeek,
                        onCurrentWeekClicked = this@MealPlannerViewModel::goToWeekPicker,
                        onMealClicked = this@MealPlannerViewModel::navigateToRecipeDetails,
                        onMealRemoveClicked = this@MealPlannerViewModel::removeMeal,
                        onBackFromWeekPlan = this@MealPlannerViewModel::goToWeekPicker
                    )
                )
            }
        }
    }

    private fun goToNextWeek() {
        selectWeek(selectedWeek!!.plusWeeks(1))
    }

    private fun goToPrevWeek() {
        selectWeek(selectedWeek!!.minusWeeks(1))
    }

    private fun goToWeekPicker() {
        setUiData(
            uiState.data!!.copy(
                selectedWeekPlan = null,
                weekTimestamps = weekTimestampPagedFlow()
            )
        )
    }

    private fun navigateToRecipeDetails(recipeId: String) {
        navigateTo(NavigationEvent.RecipeDetails(recipeId))
    }

    private fun addMeal(day: Timestamp) {
        pickingForDay = day
        navigateTo(NavigationEvent.Recipes(isPickMode = true))
    }

    private fun removeMeal(recipeId: String) {

    }

    fun onRecipePicked(recipeId: String?) {
        if (recipeId != null && pickingForDay != null) {
            viewModelScope.launch {
                runAction {
                    addMealToPlanUseCase(recipeId, pickingForDay!!)
                }
            }
        }
        pickingForDay = null
    }

    companion object {
        const val PICKED_RECIPE_ID_RESULT_KEY = "recipeId"
    }
}