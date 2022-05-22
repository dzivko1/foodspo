package altline.foodspo.ui.core.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun AppBottomNavigation(
    destinations: List<AppDestination>,
    currentDestination: AppDestination,
    onDestinationSelected: (AppDestination) -> Unit
) {
    BottomNavigation {
        for (dest in destinations) {
            BottomNavigationItem(
                label = { Text(stringResource(dest.title), softWrap = false) },
                icon = { Icon(dest.icon!!, contentDescription = null) },
                selected = currentDestination == dest,
                onClick = { onDestinationSelected(dest) }
            )
        }
    }
}