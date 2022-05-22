package altline.foodspo.ui.core.navigation

import altline.foodspo.R
import altline.foodspo.ui.explore.ExploreScreen
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry

enum class AppDestination(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector?,
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
        content = { ExploreScreen() }
    ),
    Shopping(
        route = "shopping",
        title = R.string.destination_title_shopping,
        icon = Icons.Filled.ShoppingCart,
        content = { ExploreScreen() }
    ),
    MealPlanner(
        route = "mealPlanner",
        title = R.string.destination_title_meal_planner,
        icon = Icons.Filled.RestaurantMenu,
        content = { ExploreScreen() }
    );
    
    companion object {
        val topDestinations = listOf(
            Explore, Recipes, Shopping, MealPlanner
        )
        
        fun fromRoute(route: String): AppDestination? {
            return values().find { it.route == route }
        }
    }
}