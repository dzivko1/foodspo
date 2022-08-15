package altline.foodspo.buildsrc

object Libs {
    
    object Kotlin {
        const val version = "1.6.10"
        
        const val kotlinAndroidPlugin = "org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:$version"
    }
    
    object KotlinX {
        object Coroutines {
            private const val version = "1.6.4"
            
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val playServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$version"
        }
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
    
    object Google {
        const val googleServicesPlugin = "com.google.gms:google-services:4.3.13"
    }
    
    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:30.0.1"
        const val analyticsKtx = "com.google.firebase:firebase-analytics-ktx"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val auth = "com.google.firebase:firebase-auth"
        const val uiAuth = "com.firebaseui:firebase-ui-auth:7.2.0"
    }
    
    object Accompanist {
        private const val version = "0.23.1"
        
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
    }
    
    object ComposeHtml {
        const val composeHtml = "com.github.ireward:compose-html:1.0.2"
    }
    
    object Hilt {
        const val version = "2.42"
        
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
        
        const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
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
    
    object Chucker {
        private const val version = "3.5.2"
        
        const val library = "com.github.chuckerteam.chucker:library:$version"
        const val noOp = "com.github.chuckerteam.chucker:library-no-op:$version"
    }
    
    object JUnit {
        const val junit = "junit:junit:4.13.2"
    }
}