package altline.foodspo.ui.core

import altline.foodspo.ui.core.navigation.AppBottomNavigation
import altline.foodspo.ui.core.navigation.AppDestination
import altline.foodspo.ui.core.navigation.NavGraph
import altline.foodspo.ui.theme.FoodspoTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ViewBase() {
    val appState = rememberAppState()
    
    val startDestination = AppDestination.Explore
    
    val navBackStackEntry = appState.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: startDestination.route
    val currentDestination = AppDestination.fromRoute(currentRoute)!!
    
    FoodspoTheme {
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
                    onDestinationSelected = { appState.navigateToAppDestination(it) }
                )
            }
        ) {
            NavGraph(
                navController = appState.navController,
                destinations = AppDestination.values().asList(),
                startDestination = startDestination
            )
        }
    }
}