package altline.foodspo.buildsrc

object Libs {
    
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
        
        object Test {
            const val junit = "androidx.test.ext:junit:1.1.3"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }
    
    object Accompanist {
        private const val version = "0.23.1"
        
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
    }
    
    object JUnit {
        const val junit = "junit:junit:4.13.2"
    }
}