package altline.foodspo.ui.core

import altline.foodspo.ui.core.navigation.AppDestination
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class AppState(
    val navController: NavHostController
) {
    
    fun navigateToAppDestination(destination: AppDestination) {
        navController.navigate(destination.route)
    }
    
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AppState(navController)
}