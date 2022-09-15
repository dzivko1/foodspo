package altline.foodspo.ui.screen.mealPlanner

import altline.foodspo.data.WEEK_PAGE_SIZE
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.ui.core.ScreenBase
import altline.foodspo.ui.core.navigation.ExpectNavResult
import altline.foodspo.ui.meal.component.MealPlan
import altline.foodspo.ui.meal.component.MealPlanUi
import altline.foodspo.ui.screen.mealPlanner.component.WeekListItem
import altline.foodspo.ui.theme.AppTheme
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.Month

data class MealPlannerScreenUi(
    val selectedWeekPlan: MealPlanUi?,
    val weekTimestamps: Flow<PagingData<Timestamp>>?,
    val onWeekClick: (Timestamp) -> Unit,
    val onBackFromWeekPlan: () -> Unit
) {
    companion object {
        @Composable
        fun preview() = MealPlannerScreenUi(
            selectedWeekPlan = MealPlanUi.preview(),
            weekTimestamps = flowOf(
                PagingData.from(
                    listOf(
                        Timestamp.now(),
                        Timestamp.now(),
                        Timestamp.now()
                    )
                )
            ),
            onWeekClick = {},
            onBackFromWeekPlan = {}
        )
    }
}

@Composable
fun MealPlannerScreen(viewModel: MealPlannerViewModel = hiltViewModel()) {
    ScreenBase(viewModel) {
        Content(it)
    }

    ExpectNavResult<String?>(MealPlannerViewModel.PICKED_RECIPE_ID_RESULT_KEY) { pickedRecipeId ->
        viewModel.onRecipePicked(pickedRecipeId)
    }
}

@Composable
private fun Content(data: MealPlannerScreenUi) {
    if (data.selectedWeekPlan == null) WeekPicker(data)
    else {
        MealPlan(data.selectedWeekPlan)
        BackHandler(onBack = data.onBackFromWeekPlan)
    }
}

@Composable
private fun WeekPicker(data: MealPlannerScreenUi) {
    val pagedWeekTimestamps = data.weekTimestamps?.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = WEEK_PAGE_SIZE)

    if (pagedWeekTimestamps != null) {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xxxl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(pagedWeekTimestamps, key = { it.seconds }) { timestamp ->
                if (timestamp != null) {
                    val date = timestamp.toLocalDate()
                    if (date.month == Month.DECEMBER &&
                        date.plusWeeks(1).month == Month.JANUARY
                    ) {
                        Text(
                            text = (date.year + 1).toString(),
                            style = AppTheme.typography.h6
                        )
                        Spacer(Modifier.height(AppTheme.spaces.xxl))
                    }
                    WeekListItem(
                        timestamp,
                        onClick = { data.onWeekClick(timestamp) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWeekPicker() {
    AppTheme {
        Surface {
            WeekPicker(MealPlannerScreenUi.preview())
        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    AppTheme {
        Surface {
            Content(MealPlannerScreenUi.preview())
        }
    }
}