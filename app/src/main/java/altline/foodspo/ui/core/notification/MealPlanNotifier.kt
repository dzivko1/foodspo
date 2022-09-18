package altline.foodspo.ui.core.notification

import altline.foodspo.R
import altline.foodspo.data.meal.MealRepository
import altline.foodspo.data.util.atStartOfDay
import altline.foodspo.data.util.atStartOfWeek
import android.app.AlarmManager
import android.app.Notification
import android.content.Context
import androidx.core.content.getSystemService
import com.google.firebase.Timestamp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MealPlanNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: AppNotificationManager,
    private val mealRepository: MealRepository
) {
    @OptIn(DelicateCoroutinesApi::class)
    fun scheduleMealPlannerNotifications() {
        val alarmManager = context.getSystemService<AlarmManager>()
        if (alarmManager != null) {
            val windowStartMillis = Calendar.getInstance().run {
                timeInMillis = System.currentTimeMillis()
                if (get(Calendar.HOUR_OF_DAY) >= 8) add(Calendar.DAY_OF_YEAR, 1)
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                timeInMillis
            }
            val windowLengthMillis = 60 * 60 * 1000L

            val onAlarm = AlarmManager.OnAlarmListener {
                GlobalScope.launch {
                    val notificationText =
                        mealRepository.getMealPlan(Timestamp.now().atStartOfWeek()).first()
                            .mealsByDay[Timestamp.now().atStartOfDay()]
                            ?.joinToString(",\n") { it.recipeTitle }

                    val notification = Notification.Builder(
                        context,
                        AppNotificationManager.MEAL_PLANNER_CHANNEL_ID
                    )
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(context.getString(R.string.notification_title_today_plan))
                        .setContentText(notificationText)
                        .setAutoCancel(true)
                        .build()

                    notificationManager.sendNotification(0, notification)
                    scheduleMealPlannerNotifications()
                }
            }

            alarmManager.setWindow(
                AlarmManager.RTC_WAKEUP,
                windowStartMillis,
                windowLengthMillis,
                "mealPlannerListenerTag",
                onAlarm,
                null
            )
        }
    }
}