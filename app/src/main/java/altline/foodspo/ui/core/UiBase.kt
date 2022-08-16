package altline.foodspo.ui.core

import altline.foodspo.R
import altline.foodspo.ui.core.navigation.AppBottomNavigation
import altline.foodspo.ui.core.navigation.AppDestination
import altline.foodspo.ui.core.navigation.NavGraph
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun UiBase() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val (fab, setFab) = remember { mutableStateOf<@Composable () -> Unit>({}) }

    val startDestination = AppDestination.Explore

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: startDestination.route
    val currentDestination = AppDestination.fromRoute(currentRoute)!!

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalScaffoldState provides scaffoldState,
        LocalFabSetter provides setFab
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(currentDestination.title)) },
                    navigationIcon = if (currentDestination !in AppDestination.topDestinations) {
                        {
                            IconButton(onClick = navController::navigateUp) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = stringResource(R.string.content_desc_navigate_up)
                                )
                            }
                        }
                    } else null
                )
            },
            bottomBar = {
                if (currentDestination in AppDestination.topDestinations) {
                    AppBottomNavigation(
                        destinations = AppDestination.topDestinations,
                        currentDestination = currentDestination,
                        onDestinationSelected = { navController.navigate(it.route) }
                    )
                }
            },
            floatingActionButton = fab
        ) { paddingValues ->
            NavGraph(
                navController = navController,
                destinations = AppDestination.values().asList(),
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}