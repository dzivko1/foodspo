package altline.foodspo.ui.core.component

import altline.foodspo.R
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BarDropdownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    menuItems: @Composable ColumnScope.() -> Unit
) {
    IconButton(onClick = { onExpandedChange(true) }) {
        Icon(
            Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.content_desc_more_actions)
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange(false) },
        content = menuItems
    )
}