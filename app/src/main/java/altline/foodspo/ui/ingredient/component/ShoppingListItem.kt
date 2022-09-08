package altline.foodspo.ui.ingredient.component

import altline.foodspo.R
import altline.foodspo.ui.core.component.EditorTextField
import altline.foodspo.ui.theme.AppTheme
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.DismissValue.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShoppingListItemUi(
    val id: String,
    val text: String,
    val onTextChange: (String) -> Unit,
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit,
    val editing: Boolean,
    val onEditingChange: (Boolean) -> Unit,
    val onRemove: () -> Unit
) {
    companion object {
        @Composable
        fun preview(): ShoppingListItemUi {
            var text by remember { mutableStateOf("Beans") }
            var checked by remember { mutableStateOf(false) }
            var editing by remember { mutableStateOf(false) }
            return ShoppingListItemUi(
                id = "",
                text = text,
                onTextChange = { text = it },
                checked = checked,
                onCheckedChange = { checked = it },
                editing = editing,
                onEditingChange = { editing = it },
                onRemove = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShoppingListItem(
    data: ShoppingListItemUi
) {
    val dismissState = rememberDismissState()
    if (dismissState.isDismissed(StartToEnd) || dismissState.isDismissed(EndToStart)) {
        LaunchedEffect(dismissState) {
            data.onRemove()
            dismissState.reset()
        }
    }

    SwipeToDismiss(
        state = dismissState,
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    Default -> Color.LightGray
                    DismissedToEnd, DismissedToStart -> Color.Red.copy(alpha = 0.5f)
                }
            )
            val alignment = when (direction) {
                StartToEnd -> Alignment.CenterStart
                EndToStart -> Alignment.CenterEnd
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == Default) 0.75f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.content_desc_item_deleted),
                    modifier = Modifier.scale(scale)
                )
            }
        }
    ) {
        Row(
            Modifier.padding(start = AppTheme.spaces.xl, end = AppTheme.spaces.small),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (data.editing) {
                EditorTextField(
                    text = data.text,
                    onTextChange = data.onTextChange,
                    onApply = { data.onEditingChange(false) },
                    Modifier.weight(1f),
                    placeholder = { Text(stringResource(R.string.ingredient_placeholder)) }
                )
            } else {
                Text(
                    data.text,
                    Modifier
                        .weight(1f)
                        .clickable { data.onEditingChange(true) }
                )
            }
            Checkbox(
                checked = data.checked,
                onCheckedChange = data.onCheckedChange
            )
        }
    }
}

@Preview
@Composable
private fun PreviewShoppingListItem() {
    AppTheme {
        Surface {
            ShoppingListItem(ShoppingListItemUi.preview())
        }
    }
}