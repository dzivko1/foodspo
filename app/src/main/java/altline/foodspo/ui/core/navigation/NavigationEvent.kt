package altline.foodspo.ui.core.navigation

import altline.foodspo.ui.screen.recipeDetails.RecipeDetailsViewModel
import altline.foodspo.ui.screen.recipeEditor.RecipeEditorViewModel
import altline.foodspo.ui.screen.recipes.RecipesViewModel
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

    data class Recipes(val isPickMode: Boolean = false) : NavigationEvent(
        destination = AppDestination.Recipes,
        args = mapOf(RecipesViewModel.IS_PICK_MODE_NAV_ARG to isPickMode)
    )

    object Shopping : NavigationEvent(AppDestination.Shopping)

    object MealPlanner : NavigationEvent(AppDestination.MealPlanner)

    data class RecipeDetails(val recipeId: String) : NavigationEvent(
        destination = AppDestination.RecipeDetails,
        args = mapOf(RecipeDetailsViewModel.RECIPE_ID_NAV_ARG to recipeId)
    )

    data class RecipeEditor(val recipeId: String?) : NavigationEvent(
        destination = AppDestination.RecipeEditor,
        args = recipeId?.let { mapOf(RecipeEditorViewModel.RECIPE_ID_NAV_ARG to recipeId) }
            ?: emptyMap()
    )

    // Special events, the specified destination is ignored here
    object NavigateUp : NavigationEvent(AppDestination.Explore)
    object NavigateBack : NavigationEvent(AppDestination.Explore)

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
