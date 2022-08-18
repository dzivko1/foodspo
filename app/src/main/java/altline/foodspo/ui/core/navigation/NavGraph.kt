package altline.foodspo.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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
    // The single owner used for all destinations so that viewModels don't get recreated on every navigation
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided for nav graph"
    }

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination.route
    ) {
        for (dest in destinations) {
            composable(
                route = dest.route,
                arguments = dest.arguments
            ) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    dest.content(it)
                }
            }
        }

    }
}