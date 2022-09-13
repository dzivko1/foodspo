package altline.foodspo.ui.meal

import altline.foodspo.data.meal.model.Meal
import altline.foodspo.data.meal.model.MealPlan
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.ui.core.SHORT_DAY_MONTH_FORMATTER
import altline.foodspo.ui.meal.component.MealPlanUi
import altline.foodspo.ui.meal.component.MealUi
import altline.foodspo.ui.screen.mealPlanner.MealPlannerScreenUi
import com.google.firebase.Timestamp
import javax.inject.Inject

class MealUiMapper @Inject constructor() {

    fun toMealPlannerScreenUi(
        raw: MealPlan?,
        onWeekClicked: (Timestamp) -> Unit,
        onNextWeekClicked: () -> Unit,
        onPrevWeekClicked: () -> Unit,
        onCurrentWeekClicked: () -> Unit,
        onAddMealClicked: () -> Unit,
        onMealClicked: (id: String) -> Unit,
        onMealRemoveClicked: (id: String) -> Unit,
        onBackFromWeekPlan: () -> Unit
    ) = MealPlannerScreenUi(
        selectedWeekPlan = raw?.let {
            toMealPlanUi(
                raw,
                onNextWeekClicked,
                onPrevWeekClicked,
                onCurrentWeekClicked,
                onAddMealClicked,
                onMealClicked,
                onMealRemoveClicked
            )
        },
        weekTimestamps = null,
        onWeekClick = onWeekClicked,
        onBackFromWeekPlan = onBackFromWeekPlan
    )

    fun toMealPlanUi(
        raw: MealPlan,
        onNextWeekClicked: () -> Unit,
        onPrevWeekClicked: () -> Unit,
        onCurrentWeekClicked: () -> Unit,
        onAddMealClicked: () -> Unit,
        onMealClicked: (id: String) -> Unit,
        onMealRemoveClicked: (id: String) -> Unit
    ): MealPlanUi {
        val fromDate = raw.weekTimestamp.toLocalDate().format(SHORT_DAY_MONTH_FORMATTER)
        val toDate = raw.weekTimestamp.toLocalDate().plusDays(6).format(SHORT_DAY_MONTH_FORMATTER)
        return MealPlanUi(
            week = "$fromDate - $toDate",
            mealsByDay = raw.mealsByDay.entries.associate { (timestamp, meals) ->
                timestamp.toLocalDate().format(SHORT_DAY_MONTH_FORMATTER) to
                    meals.map { meal ->
                        toMealUi(
                            meal,
                            onContentClicked = onMealClicked,
                            onRemoveClicked = onMealRemoveClicked
                        )
                    }
            },
            onNextWeekClick = onNextWeekClicked,
            onPrevWeekClick = onPrevWeekClicked,
            onCurrentWeekClick = onCurrentWeekClicked,
            onAddMealClick = onAddMealClicked
        )
    }

    fun toMealUi(
        raw: Meal,
        onContentClicked: (id: String) -> Unit,
        onRemoveClicked: (id: String) -> Unit
    ): MealUi {
        return MealUi(
            id = raw.id,
            title = raw.recipeTitle,
            image = raw.recipeImage,
            onContentClick = onContentClicked,
            onRemoveClick = onRemoveClicked
        )
    }
}