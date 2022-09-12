package altline.foodspo.ui.meal

import altline.foodspo.data.meal.model.Meal
import altline.foodspo.data.meal.model.MealPlan
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.ui.meal.component.MealPlanUi
import altline.foodspo.ui.meal.component.MealUi
import altline.foodspo.ui.screen.mealPlanner.MealPlannerScreenUi
import com.google.firebase.Timestamp
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MealUiMapper @Inject constructor() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd.M")

    fun toMealPlannerScreenUi(
        raw: MealPlan?,
        onWeekClicked: (Timestamp) -> Unit,
        onNextWeekClicked: () -> Unit,
        onPrevWeekClicked: () -> Unit,
        onAddMealClicked: () -> Unit,
        onMealClicked: (id: String) -> Unit,
        onMealRemoveClicked: (id: String) -> Unit
    ) = MealPlannerScreenUi(
        selectedWeekPlan = raw?.let {
            toMealPlanUi(
                raw,
                onNextWeekClicked,
                onPrevWeekClicked,
                onAddMealClicked,
                onMealClicked,
                onMealRemoveClicked
            )
        },
        weekTimestamps = null,
        onWeekClick = onWeekClicked
    )

    fun toMealPlanUi(
        raw: MealPlan,
        onNextWeekClicked: () -> Unit,
        onPrevWeekClicked: () -> Unit,
        onAddMealClicked: () -> Unit,
        onMealClicked: (id: String) -> Unit,
        onMealRemoveClicked: (id: String) -> Unit
    ): MealPlanUi {
        val fromDate = raw.weekTimestamp.toLocalDate().format(dateFormatter)
        val toDate = raw.weekTimestamp.toLocalDate().plusWeeks(1).format(dateFormatter)
        return MealPlanUi(
            week = "$fromDate - $toDate",
            mealsByDay = raw.mealsByDay.entries.associate { (timestamp, meals) ->
                timestamp.toLocalDate().format(dateFormatter) to
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