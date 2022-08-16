package altline.foodspo.ui.core.component

import altline.foodspo.R
import altline.foodspo.util.modifiedColor
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun SaveButton(
    isSaved: Boolean,
    onSavedChange: (saved: Boolean) -> Unit
) {
    IconToggleButton(checked = isSaved, onCheckedChange = onSavedChange) {
        if (isSaved) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(R.string.content_desc_drop_recipe),
                tint = modifiedColor(alpha = ContentAlpha.high)
            )
        } else {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.content_desc_save_recipe),
                tint = modifiedColor(alpha = ContentAlpha.medium)
            )
        }
    }
}