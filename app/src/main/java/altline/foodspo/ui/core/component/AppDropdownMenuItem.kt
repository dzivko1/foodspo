package altline.foodspo.ui.core.component

import altline.foodspo.ui.theme.AppTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppDropdownMenuItem(
    text: String,
    icon: ImageVector? = null,
    iconContentDescription: String? = null,
    onClick: () -> Unit
) {
    DropdownMenuItem(onClick) {
        if (icon != null) {
            Icon(
                icon,
                contentDescription = iconContentDescription
            )
            Spacer(Modifier.width(AppTheme.spaces.large))
        }
        Text(text)
    }
}