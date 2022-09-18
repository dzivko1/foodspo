package altline.foodspo.ui.core.notification

import altline.foodspo.R
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val manager = NotificationManagerCompat.from(context)

    fun createMealPlannerChannel() {
        val name = context.getString(R.string.channel_name_meal_planner)
        manager.createNotificationChannel(
            NotificationChannelCompat.Builder(
                MEAL_PLANNER_CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
                .setName(name)
                .build()
        )
    }

    fun sendNotification(notificationId: Int, notification: Notification) {
        manager.notify(notificationId, notification)
    }

    companion object {
        const val MEAL_PLANNER_CHANNEL_ID = "mealPlanner"
    }
}