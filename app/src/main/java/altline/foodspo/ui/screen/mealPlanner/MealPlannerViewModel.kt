package altline.foodspo.ui.screen.mealPlanner

import altline.foodspo.data.WEEK_PAGE_SIZE
import altline.foodspo.data.core.paging.IndexedPagingSource
import altline.foodspo.data.util.minusWeeks
import altline.foodspo.data.util.plusWeeks
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.data.util.toTimestamp
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MealPlannerViewModel @Inject constructor(
    private val getMealPlanUseCase: GetMealPlanUseCase,
    private val mealUiMapper: MealUiMapper
) : ViewModelBase<MealPlannerScreenUi>() {

    private var selectedWeek: Timestamp? = null

    init {
        loadData()
    }

    override fun loadData() {
        setUiData(
            MealPlannerScreenUi(
                selectedWeekPlan = null,
                weekTimestamps = weekTimestampPagedFlow(),
                onWeekClick = this::selectWeek
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
                        val start = Timestamp.now().toLocalDate()
                            .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)
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
        viewModelScope.launch {
            runAction {
                getMealPlanUseCase(timestamp).also { selectedWeek = it.weekTimestamp }
            }.onSuccess { mealPlan ->
                setUiData(
                    mealUiMapper.toMealPlannerScreenUi(
                        mealPlan,
                        onWeekClicked = this@MealPlannerViewModel::selectWeek,
                        onAddMealClicked = this@MealPlannerViewModel::addMeal,
                        onNextWeekClicked = this@MealPlannerViewModel::goToNextWeek,
                        onPrevWeekClicked = this@MealPlannerViewModel::goToPrevWeek,
                        onMealClicked = this@MealPlannerViewModel::navigateToRecipeDetails,
                        onMealRemoveClicked = this@MealPlannerViewModel::removeMeal
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

    private fun navigateToRecipeDetails(recipeId: String) {
        navigateTo(NavigationEvent.RecipeDetails(recipeId))
    }

    private fun addMeal() {

    }

    private fun removeMeal(recipeId: String) {

    }
}