package altline.foodspo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalSpaces provides Spaces()
    ) {
        MaterialTheme(
            colors = themedColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object AppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colors
    
    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
    
    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes
    
    val spaces: Spaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current
}