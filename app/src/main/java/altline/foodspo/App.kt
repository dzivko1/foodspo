package altline.foodspo

import altline.foodspo.ui.core.notification.AppNotificationManager
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var notificationManager: AppNotificationManager

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        notificationManager.createMealPlannerChannel()
    }
}