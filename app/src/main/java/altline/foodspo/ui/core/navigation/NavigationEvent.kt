package altline.foodspo.ui.core.navigation

import androidx.navigation.NavHostController

sealed class NavigationEvent(
    private val destination: AppDestination,
    private val args: Map<String, Any> = emptyMap()
) {
    
    object Explore : NavigationEvent(AppDestination.Explore)
    object Recipes : NavigationEvent(AppDestination.Recipes)
    object Shopping : NavigationEvent(AppDestination.Shopping)
    object MealPlanner : NavigationEvent(AppDestination.MealPlanner)
    
    data class RecipeDetails(val recipeId: String) : NavigationEvent(
        destination = AppDestination.RecipeDetails,
        args = mapOf("recipeId" to recipeId)
    )
    
    fun navigate(navController: NavHostController) {
        var argumentedRoute = destination.route
        destination.arguments.forEach { arg ->
            argumentedRoute = argumentedRoute.replace("{${arg.name}}", args[arg.name].toString())
        }
        navController.navigate(argumentedRoute)
    }
}
