package altline.foodspo.ui.meal.component

import altline.foodspo.R
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class MealPlanUi(
    val week: String,
    val mealsByDay: Map<String, List<MealUi>>,
    val onNextWeekClick: () -> Unit,
    val onPrevWeekClick: () -> Unit,
    val onCurrentWeekClick: () -> Unit,
    val onAddMealClick: () -> Unit
) {
    companion object {
        @Composable
        fun preview() = MealPlanUi(
            week = "16.5 - 22.5",
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
            onCurrentWeekClick = {},
            onAddMealClick = {}
        )
    }
}

@Composable
fun MealPlan(data: MealPlanUi) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(vertical = AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeekSelector(data)
        for ((day, meals) in data.mealsByDay) {
            DayPlan(day, meals, data.onAddMealClick)
        }
        WeekSelector(data)
    }
}

@Composable
private fun WeekSelector(data: MealPlanUi) {
    val buttonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = AppTheme.colors.secondary
    )
    Row(
        Modifier.padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        OutlinedButton(
            onClick = data.onPrevWeekClick,
            colors = buttonColors,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = stringResource(R.string.content_desc_previous_week)
            )
        }
        OutlinedButton(
            onClick = data.onCurrentWeekClick,
            Modifier.weight(1f),
            colors = buttonColors
        ) {
            Text(
                text = data.week
            )
        }
        OutlinedButton(
            onClick = data.onNextWeekClick,
            colors = buttonColors,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                Icons.Default.ArrowForwardIos,
                contentDescription = stringResource(R.string.content_desc_next_week)
            )
        }
    }
}

@Composable
private fun DayPlan(
    date: String,
    meals: List<MealUi>,
    onAddMealClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date,
            Modifier
                .padding(horizontal = AppTheme.spaces.large)
                .fillMaxWidth(),
            style = AppTheme.typography.subtitle1
        )
        Divider(Modifier.padding(horizontal = AppTheme.spaces.large))

        if (meals.isEmpty()) {
            Text(
                text = stringResource(R.string.meal_planner_no_meals_planned),
                color = modifiedColor(alpha = ContentAlpha.disabled),
                style = AppTheme.typography.subtitle2
            )
        } else {
            for (meal in meals) {
                Meal(meal)
            }
        }

        TextButton(
            onClick = onAddMealClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = AppTheme.colors.secondary
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text(stringResource(R.string.action_add))
        }
    }
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