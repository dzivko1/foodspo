package altline.foodspo.buildsrc

object Libs {
    
    object KotlinX {
        object Coroutines {
            private const val version = "1.6.1"
            
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }
    }
    
    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:30.0.1"
        const val analyticsKtx = "com.google.firebase:firebase-analytics-ktx"
        const val uiAuth = "com.firebaseui:firebase-ui-auth:7.2.0"
    }
    
    object Android {
        const val playServicesAuth = "com.google.android.gms:play-services-auth:20.2.0"
    }
    
    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        
        object Compose {
            const val version = "1.1.1"
            
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
        }
        
        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }
        
        object Lifecycle {
            const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        }
        
        object Navigation {
            const val navigationCompose = "androidx.navigation:navigation-compose:2.4.2"
        }
        
        object Paging {
            const val pagingRuntime = "androidx.paging:paging-runtime:3.1.1"
            const val pagingCompose = "androidx.paging:paging-compose:1.0.0-alpha15"
        }
        
        object Test {
            const val junit = "androidx.test.ext:junit:1.1.3"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }
    
    object Accompanist {
        private const val version = "0.23.1"
        
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
    }
    
    object Koin {
        const val koinCompose = "io.insert-koin:koin-androidx-compose:3.2.0"
    }
    
    object Timber {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }
    
    object Moshi {
        private const val version = "1.13.0"
        const val moshi = "com.squareup.moshi:moshi:$version"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }
    
    object OkHttp {
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.10.0"
    }
    
    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }
    
    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:2.1.0"
    }
    
    object JUnit {
        const val junit = "junit:junit:4.13.2"
    }
}