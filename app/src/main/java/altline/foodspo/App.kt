package altline.foodspo

import altline.foodspo.data.di.dataModule
import altline.foodspo.data.di.networkModule
import altline.foodspo.di.coreModule
import altline.foodspo.di.exploreModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                coreModule,
                networkModule,
                dataModule,
                exploreModule
            )
        }
    }
}