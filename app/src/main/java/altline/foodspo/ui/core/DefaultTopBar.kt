package altline.foodspo.ui.core

import altline.foodspo.R
import altline.foodspo.ui.core.navigation.AppDestination
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun DefaultTopBar() {
    val navController = LocalNavController.current
    val currentDestination = navController.getCurrentAppDestination()

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
}