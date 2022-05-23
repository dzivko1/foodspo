package altline.foodspo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = Yellow400,
    primaryVariant = Yellow600,
    secondary = Orange300,
    secondaryVariant = Orange500,
    onPrimary = Color.Black,
    onSecondary = Color.Black
)

private val DarkColorPalette = darkColors(
    primary = Yellow500,
    primaryVariant = Yellow600,
    secondary = Orange300,
    secondaryVariant = Orange500,
    onPrimary = Color.Black,
    onSecondary = Color.Black
)

@Composable
fun FoodspoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}