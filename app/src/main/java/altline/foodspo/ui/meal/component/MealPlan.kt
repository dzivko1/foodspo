package altline.foodspo.ui.meal.component

import altline.foodspo.ui.theme.AppTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

data class MealPlanUi(
    val week: String,
    val mealsByDay: Map<String, List<MealUi>>,
    val onNextWeekClick: () -> Unit,
    val onPrevWeekClick: () -> Unit,
    val onAddMealClick: () -> Unit
) {
    companion object {
        @Composable
        fun preview() = MealPlanUi(
            week = "16.5 - 22.5 / 2022",
            mealsByDay = mapOf(
                "16.5" to listOf(
                    MealUi.preview(),
                    MealUi.preview(),
                    MealUi.preview()
                ),
                "17.5" to listOf(),
                "18.5" to listOf(
                    MealUi.preview(),
                    MealUi.preview(),
                ),
                "19.5" to listOf(),
                "20.5" to listOf(),
                "21.5" to listOf(),
                "22.5" to listOf()
            ),
            onNextWeekClick = {},
            onPrevWeekClick = {},
            onAddMealClick = {}
        )
    }
}

@Composable
fun MealPlan(data: MealPlanUi) {

}

@Preview
@Composable
private fun PreviewMealPlan() {
    AppTheme {
        Surface {
            MealPlan(MealPlanUi.preview())
        }
    }
}