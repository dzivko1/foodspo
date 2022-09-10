package altline.foodspo.ui.ingredient.component

import altline.foodspo.R
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

data class ShoppingListGroupUi(
    val title: String,
    val items: List<ShoppingListItemUi>
) {
    companion object {
        @Composable
        fun preview() = ShoppingListGroupUi(
            title = "General",
            items = listOf(
                ShoppingListItemUi.preview(),
                ShoppingListItemUi.preview(),
                ShoppingListItemUi.preview()
            )
        )
    }
}

@Composable
fun ShoppingListGroup(
    data: ShoppingListGroupUi,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Column {
        Row(
            Modifier
                .clickable { onExpandedChange(!expanded) }
                .padding(AppTheme.spaces.xl),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.title,
                Modifier.weight(1f),
                color = modifiedColor(alpha = ContentAlpha.medium),
                style = AppTheme.typography.caption
            )
            Icon(
                if (expanded) Icons.Default.ArrowDropDown
                else Icons.Default.ArrowLeft,
                contentDescription = stringResource(R.string.content_desc_expand_shrink)
            )
        }

        AnimatedVisibility(
            visible = expanded
        ) {
            Column {
                for (item in data.items) {
                    ShoppingListItem(item)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewShoppingListGroup() {
    AppTheme {
        Surface {
            ShoppingListGroup(
                ShoppingListGroupUi.preview(),
                expanded = true,
                onExpandedChange = {}
            )
        }
    }
}