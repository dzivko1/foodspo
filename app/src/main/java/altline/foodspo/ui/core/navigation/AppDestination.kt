package altline.foodspo.ui.core.navigation

import altline.foodspo.R
import altline.foodspo.ui.screen.explore.ExploreScreen
import altline.foodspo.ui.screen.mealPlanner.MealPlannerScreen
import altline.foodspo.ui.screen.recipeDetails.RecipeDetailsScreen
import altline.foodspo.ui.screen.recipeDetails.RecipeDetailsViewModel
import altline.foodspo.ui.screen.recipeEditor.RecipeEditorScreen
import altline.foodspo.ui.screen.recipeEditor.RecipeEditorViewModel
import altline.foodspo.ui.screen.recipes.RecipesScreen
import altline.foodspo.ui.screen.shopping.ShoppingScreen
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class AppDestination(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector?,
    val arguments: List<NamedNavArgument> = emptyList(),
    val content: @Composable (NavBackStackEntry) -> Unit
) {
    Explore(
        route = "explore",
        title = R.string.destination_title_explore,
        icon = Icons.Filled.Explore,
        content = { ExploreScreen() }
    ),
    Recipes(
        route = "recipes",
        title = R.string.destination_title_recipes,
        icon = Icons.Filled.MenuBook,
        content = { RecipesScreen() }
    ),
    Shopping(
        route = "shopping",
        title = R.string.destination_title_shopping,
        icon = Icons.Filled.ShoppingCart,
        content = { ShoppingScreen() }
    ),
    MealPlanner(
        route = "mealPlanner",
        title = R.string.destination_title_meal_planner,
        icon = Icons.Filled.RestaurantMenu,
        content = { MealPlannerScreen() }
    ),
    RecipeDetails(
        route = "recipeDetails/{${RecipeDetailsViewModel.RECIPE_ID_NAV_ARG}}",
        title = R.string.destination_title_recipe_details,
        icon = null,
        arguments = listOf(
            navArgument(RecipeDetailsViewModel.RECIPE_ID_NAV_ARG) { type = NavType.StringType }
        ),
        content = { RecipeDetailsScreen() }
    ),
    RecipeEditor(
        route = "recipeEditor/{${RecipeEditorViewModel.RECIPE_ID_NAV_ARG}}",
        title = R.string.destination_title_new_recipe,
        icon = null,
        arguments = listOf(
            navArgument(RecipeEditorViewModel.RECIPE_ID_NAV_ARG) { type = NavType.StringType }
        ),
        content = { RecipeEditorScreen() }
    );

    fun constructArgumentedRoute(args: Map<String, Any>): String {
        var argumentedRoute = route
        arguments.forEach { arg ->
            argumentedRoute = argumentedRoute.replace("{${arg.name}}", args[arg.name].toString())
        }
        return argumentedRoute
    }

    companion object {
        val topDestinations = listOf(
            Explore, Recipes, Shopping, MealPlanner
        )

        fun fromRoute(route: String): AppDestination? {
            return values().find { it.route == route }
        }
    }
}