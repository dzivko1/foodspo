package altline.foodspo

import altline.foodspo.ui.core.ViewBase
import altline.foodspo.ui.theme.AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.primarySurface
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(AppTheme.colors.primarySurface)
                
                ViewBase()
            }
        }
    }
}
