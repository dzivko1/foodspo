package altline.foodspo.ui.core

import altline.foodspo.ui.core.navigation.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState

val LocalNavController = staticCompositionLocalOf<AppNavController> {
    error("No LocalNavController provided")
}

@Composable
fun ViewBase() {
    val appNavController = rememberAppNavController()
    
    val startDestination = AppDestination.Explore
    
    val navBackStackEntry = appNavController.navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: startDestination.route
    val currentDestination = AppDestination.fromRoute(currentRoute)!!
    
    CompositionLocalProvider(
        LocalNavController provides appNavController
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(currentDestination.title)) }
                )
            },
            bottomBar = {
                AppBottomNavigation(
                    destinations = AppDestination.topDestinations,
                    currentDestination = currentDestination,
                    onDestinationSelected = { appNavController.navigateToAppDestination(it) }
                )
            }
        ) {
            NavGraph(
                navController = appNavController.navHostController,
                destinations = AppDestination.values().asList(),
                startDestination = startDestination
            )
        }
    }
}