package altline.foodspo.ui.screen.mealPlanner.component

import altline.foodspo.data.util.toLocalDate
import altline.foodspo.ui.theme.AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import java.time.format.DateTimeFormatter

@Composable
fun WeekListItem(
    weekTimestamp: Timestamp,
    onClick: () -> Unit
) {
    val date = weekTimestamp.toDate().toLocalDate()
    val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    Column(
        Modifier
            .clickable(onClick = onClick)
            .background(AppTheme.colors.primary.copy(alpha = 0.1f))
    ) {
        Divider()
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = AppTheme.spaces.xxl, vertical = AppTheme.spaces.medium),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.width(30.dp)) {
                Text(
                    text = date.format(monthFormatter).uppercase(),
                    style = AppTheme.typography.overline
                )
                val month = date.monthValue
                val weekAfter = date.plusWeeks(1)
                if (month != weekAfter.monthValue) {
                    Text(
                        text = weekAfter.format(monthFormatter).uppercase(),
                        style = AppTheme.typography.overline
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = AppTheme.colors.onSurface.copy(alpha = 0.12f))
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (weekday in 0..6) {
                    val day = date.plusDays(weekday.toLong())
                    Text(
                        text = day.dayOfMonth.toString(),
                        style = AppTheme.typography.body2
                    )
                }
            }
        }
        Divider()
    }
}

@Preview
@Composable
private fun PreviewWeekListItem() {
    AppTheme {
        Surface {
            WeekListItem(
                weekTimestamp = Timestamp.now(),
                onClick = {}
            )
        }
    }
}