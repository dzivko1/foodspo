package altline.foodspo.ui.meal

import altline.foodspo.data.meal.model.Meal
import altline.foodspo.data.meal.model.MealPlan
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.ui.core.SHORT_DAY_MONTH_FORMATTER
import altline.foodspo.ui.meal.component.MealPlanUi
import altline.foodspo.ui.meal.component.MealUi
import altline.foodspo.ui.placeholder.PlaceholderImages
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
        onAddMealClicked: (day: Timestamp) -> Unit,
        onMealClicked: (id: String) -> Unit,
        onMealRemoveClicked: (id: String, day: Timestamp) -> Unit,
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
        onAddMealClicked: (day: Timestamp) -> Unit,
        onMealClicked: (id: String) -> Unit,
        onMealRemoveClicked: (id: String, day: Timestamp) -> Unit
    ): MealPlanUi {
        val fromDate = raw.weekTimestamp.toLocalDate().format(SHORT_DAY_MONTH_FORMATTER)
        val toDate = raw.weekTimestamp.toLocalDate().plusDays(6).format(SHORT_DAY_MONTH_FORMATTER)
        val timestampToDateString = raw.mealsByDay.mapValues { (timestamp, _) ->
            timestamp.toLocalDate().format(SHORT_DAY_MONTH_FORMATTER)
        }
        return MealPlanUi(
            week = "$fromDate - $toDate",
            mealsByDay = raw.mealsByDay.entries.associate { (timestamp, meals) ->
                timestampToDateString[timestamp]!! to
                    meals.map { meal ->
                        toMealUi(
                            meal,
                            onContentClicked = onMealClicked,
                            onRemoveClicked = { onMealRemoveClicked(it, timestamp) }
                        )
                    }
            },
            onNextWeekClick = onNextWeekClicked,
            onPrevWeekClick = onPrevWeekClicked,
            onCurrentWeekClick = onCurrentWeekClicked,
            onAddMealClick = { dateString ->
                val timestamp = timestampToDateString.filterValues { it == dateString }.keys.first()
                onAddMealClicked(timestamp)
            }
        )
    }

    fun toMealUi(
        raw: Meal,
        onContentClicked: (id: String) -> Unit,
        onRemoveClicked: (id: String) -> Unit
    ): MealUi {
        return MealUi(
            mealId = raw.id,
            recipeId = raw.recipeId,
            title = raw.recipeTitle,
            image = raw.recipeImage ?: PlaceholderImages.recipe,
            onContentClick = onContentClicked,
            onRemoveClick = onRemoveClicked
        )
    }
}