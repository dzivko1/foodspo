package altline.foodspo

import altline.foodspo.ui.core.UiBase
import altline.foodspo.ui.theme.AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.primarySurface
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize()

        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(AppTheme.colors.primarySurface)
                
                UiBase()
            }
        }
    }
}
