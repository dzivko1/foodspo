package altline.foodspo.ui.core

import altline.foodspo.R
import altline.foodspo.ui.core.navigation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
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
                    title = { Text(stringResource(currentDestination.title)) },
                    navigationIcon = if (currentDestination !in AppDestination.topDestinations) {
                        {
                            IconButton(onClick = appNavController::navigateUp) {
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
                        onDestinationSelected = { appNavController.navigateToAppDestination(it) }
                    )
                }
            }
        ) { paddingValues ->
            NavGraph(
                navController = appNavController.navHostController,
                destinations = AppDestination.values().asList(),
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}