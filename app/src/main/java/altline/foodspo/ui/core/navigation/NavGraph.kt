package altline.foodspo.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    destinations: List<AppDestination>,
    startDestination: AppDestination
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination.route
    ) {
        for (dest in destinations) {
            composable(
                route = dest.route,
                arguments = dest.arguments,
                content = {
                    dest.content(it)
                }
            )
        }
    }
}