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

    // Special events, the specified destination is ignored here
    object NavigateUp : NavigationEvent(AppDestination.Explore)
    object NavigateBack : NavigationEvent(AppDestination.Explore)

    data class RecipeDetails(val recipeId: String) : NavigationEvent(
        destination = AppDestination.RecipeDetails,
        args = mapOf("recipeId" to recipeId)
    )

    data class RecipeEditor(val recipeId: String?) : NavigationEvent(
        destination = AppDestination.RecipeEditor,
        args = recipeId?.let { mapOf("recipeId" to recipeId) } ?: emptyMap()
    )

    fun navigate(navController: NavHostController) {
        when (this) {
            is NavigateUp -> navController.navigateUp()
            is NavigateBack -> navController.popBackStack()
            else -> navController.navigate(constructRoute())
        }
    }

    private fun constructRoute(): String {
        var argumentedRoute = destination.route
        destination.arguments.forEach { arg ->
            argumentedRoute = argumentedRoute.replace("{${arg.name}}", args[arg.name].toString())
        }
        return argumentedRoute
    }
}
