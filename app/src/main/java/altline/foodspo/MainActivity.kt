package altline.foodspo

import altline.foodspo.ui.core.UiBase
import altline.foodspo.ui.theme.AppTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.primarySurface
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize()

        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            if (auth.currentUser == null) onSignOut()
        }

        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(AppTheme.colors.primarySurface)

                UiBase()
            }
        }
    }

    private fun onSignOut() {
        startActivity(
            Intent(this, LauncherActivity::class.java)
        )
        finish()
    }
}
