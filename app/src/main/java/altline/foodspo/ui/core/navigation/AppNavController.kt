package altline.foodspo.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class AppNavController(
    val navHostController: NavHostController
) {
    fun navigateToAppDestination(destination: AppDestination) {
        navHostController.navigate(destination.route)
    }
    
    fun navigateToRecipeDetails(recipeId: Long) {
        val route = AppDestination.recipeDetailsRoute(recipeId)
        navHostController.navigate(route)
    }
    
    fun navigateUp() = navHostController.navigateUp()
}

@Composable
fun rememberAppNavController(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AppNavController(navController)
}