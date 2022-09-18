package altline.foodspo

import altline.foodspo.ui.core.notification.MealPlanNotifier
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class BootNotificationScheduler : BroadcastReceiver() {

    @Inject
    lateinit var mealPlanNotifier: MealPlanNotifier

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            mealPlanNotifier.scheduleMealPlannerNotifications()
        }
    }
}
