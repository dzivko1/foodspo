package altline.foodspo.ui.core.component

import altline.foodspo.R
import altline.foodspo.ui.theme.AppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

data class ListEditorUi(
    val items: List<String>,
    val editingItemIndex: Int?,
    val onEditingItemChange: (Int, Boolean) -> Unit,
    val onItemEdit: (Int, String) -> Unit,
    val onItemAdd: () -> Unit,
    val onItemRemove: (Int) -> Unit
) {
    companion object {
        val PREVIEW = ListEditorUi(
            items = listOf(
                "Ingredient 1",
                "Ingredient 2",
                "Ingredient 3",
                "Ingredient 4"
            ),
            editingItemIndex = 2,
            onEditingItemChange = { _, _ -> },
            onItemEdit = { _, _ -> },
            onItemAdd = {},
            onItemRemove = {},
        )
    }
}

@Composable
fun ListEditor(
    data: ListEditorUi
) {
    Column {
        data.items.forEachIndexed { index, item ->
            EditableListItem(
                text = item,
                onTextChange = { data.onItemEdit(index, it) },
                editing = index == data.editingItemIndex,
                onEditingChange = { data.onEditingItemChange(index, it) },
                onRemove = { data.onItemRemove(index) }
            )
            Divider()
        }
        Row(
            Modifier
                .padding(vertical = AppTheme.spaces.xl)
                .clickable(onClick = data.onItemAdd)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text(
                text = stringResource(R.string.recipe_editor_add_ingredient_button),
                Modifier.padding(start = AppTheme.spaces.xxxl),
                style = AppTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun EditableListItem(
    text: String,
    onTextChange: (String) -> Unit,
    editing: Boolean,
    onEditingChange: (Boolean) -> Unit,
    onRemove: () -> Unit
) {
    if (editing) {
        EditorTextField(
            text = text,
            onTextChange = onTextChange,
            onApply = { onEditingChange(false) },
            placeholder = { Text(stringResource(R.string.ingredient_placeholder)) }
        )
    } else {
        Row(
            Modifier
                .padding(start = AppTheme.spaces.small)
                .clickable { onEditingChange(true) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                Modifier.weight(1f),
                style = AppTheme.typography.subtitle1
            )
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Outlined.RemoveCircleOutline,
                    contentDescription = stringResource(R.string.content_desc_remove_ingredient)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewListEditor() {
    AppTheme {
        Surface {
            ListEditor(ListEditorUi.PREVIEW)
        }
    }
}