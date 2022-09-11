package altline.foodspo.ui.core

import altline.foodspo.ui.core.navigation.AppBottomNavigation
import altline.foodspo.ui.core.navigation.AppDestination
import altline.foodspo.ui.core.navigation.NavGraph
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

val START_DESTINATION = AppDestination.Explore

@Composable
fun UiBase() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val (topBar, setTopBar) = remember { mutableStateOf<@Composable () -> Unit>({}) }
    val (fab, setFab) = remember { mutableStateOf<(@Composable () -> Unit)?>({}) }

    val currentDestination = navController.getCurrentAppDestination()

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalScaffoldState provides scaffoldState,
        LocalTopBarSetter provides setTopBar,
        LocalFabSetter provides setFab
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = topBar,
            bottomBar = {
                if (currentDestination in AppDestination.topDestinations) {
                    AppBottomNavigation(
                        destinations = AppDestination.topDestinations,
                        currentDestination = currentDestination,
                        onDestinationSelected = {
                            navController.navigate(it.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        }
                    )
                }
            },
            floatingActionButton = fab ?: {}
        ) { paddingValues ->
            NavGraph(
                navController = navController,
                destinations = AppDestination.values().asList(),
                startDestination = START_DESTINATION,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun NavHostController.getCurrentAppDestination(): AppDestination {
    val navBackStackEntry = currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    return currentRoute?.let { AppDestination.fromRoute(it) } ?: START_DESTINATION
}