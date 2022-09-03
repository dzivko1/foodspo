package altline.foodspo.ui.core.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

sealed class NavigationEvent(
    private val destination: AppDestination,
    private val args: Map<String, Any> = emptyMap(),
    defaultNavOptions: NavOptions? = null
) {
    var navOptions: NavOptions? = defaultNavOptions
        private set

    private var popUntil: String? = null
    private var popCount: Int = 0

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

    /**
     * Overrides the default nav options for this event.
     */
    fun withNavOptions(optionsBuilder: NavOptionsBuilder.() -> Unit): NavigationEvent {
        navOptions = navOptions(optionsBuilder)
        return this
    }

    /**
     * Sets an instruction to pop up to the given destination right before navigating to the
     * destination described by the event.
     */
    fun popUntil(destination: AppDestination): NavigationEvent {
        popUntil = destination.route
        return this
    }

    fun pop(times: Int): NavigationEvent {
        popCount = times
        return this
    }

    fun navigate(navController: NavHostController) {
        popUntil?.let {
            navController.popBackStack(it, inclusive = false)
            popUntil = null
        }

        repeat(popCount) {
            navController.popBackStack()
        }
        popCount = 0

        when (this) {
            is NavigateUp -> navController.navigateUp()
            is NavigateBack -> navController.popBackStack()
            else -> navController.navigate(destination.constructArgumentedRoute(args), navOptions)
        }
    }
}
