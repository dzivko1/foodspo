package altline.foodspo.ui.core

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No LocalNavController provided")
}

val LocalScaffoldState = staticCompositionLocalOf<ScaffoldState> {
    error("No LocalScaffoldState provided")
}

val LocalTopBarSetter = staticCompositionLocalOf<(@Composable () -> Unit) -> Unit> {
    error("No LocalTopBarSetter")
}

val LocalFabSetter = staticCompositionLocalOf<((@Composable () -> Unit)?) -> Unit> {
    error("No LocalFabSetter provided")
}