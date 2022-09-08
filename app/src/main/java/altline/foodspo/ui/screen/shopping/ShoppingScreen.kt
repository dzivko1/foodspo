package altline.foodspo.ui.screen.shopping

import altline.foodspo.R
import altline.foodspo.ui.core.ScreenBase
import altline.foodspo.ui.ingredient.component.ShoppingListGroup
import altline.foodspo.ui.ingredient.component.ShoppingListGroupUi
import altline.foodspo.ui.ingredient.component.ShoppingListItemUi
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.ProvideContentColor
import altline.foodspo.util.modifiedColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

data class ShoppingScreenUi(
    val shoppingItems: Map<String?, List<ShoppingListItemUi>>,
    val onAddItem: () -> Unit
) {
    companion object {
        @Composable
        fun preview() = ShoppingScreenUi(
            shoppingItems = mapOf(
                null to listOf(
                    ShoppingListItemUi.preview(),
                    ShoppingListItemUi.preview(),
                    ShoppingListItemUi.preview()
                ),
                "recipe1" to listOf(
                    ShoppingListItemUi.preview(),
                    ShoppingListItemUi.preview()
                ),
                "recipe2" to listOf(
                    ShoppingListItemUi.preview()
                )
            ),
            onAddItem = {}
        )
    }
}

@Composable
fun ShoppingScreen(viewModel: ShoppingViewModel = hiltViewModel()) {
    ScreenBase(
        viewModel
    ) {
        if (it.shoppingItems.isEmpty()) EmptyContent(it)
        else Content(it)
    }
}

@Composable
fun EmptyContent(
    data: ShoppingScreenUi
) {
    Column(
        Modifier.padding(AppTheme.spaces.xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddButton(onClick = data.onAddItem)
        Spacer(Modifier.height(AppTheme.spaces.xl))
        ProvideContentColor(alpha = ContentAlpha.disabled) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.large),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.ic_curly_arrow),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.shopping_empty_message),
                    Modifier.width(200.dp),
                    style = AppTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun Content(
    data: ShoppingScreenUi
) {
    LazyColumn {
        item {
            Column {
                var expanded by remember { mutableStateOf(true) }
                ShoppingListGroup(
                    ShoppingListGroupUi(
                        title = stringResource(R.string.shopping_general_category_title),
                        items = data.shoppingItems[null] ?: emptyList()
                    ),
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )
                AnimatedVisibility(
                    visible = expanded
                ) {
                    AddButton(
                        onClick = data.onAddItem,
                        Modifier.padding(start = AppTheme.spaces.xxxl),
                        contentColor = modifiedColor(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
        items(data.shoppingItems.filterKeys { it != null }.toList()) { (title, items) ->
            var expanded by remember { mutableStateOf(true) }
            ShoppingListGroup(
                ShoppingListGroupUi(
                    title = title!!,
                    items = items
                ),
                expanded = expanded,
                onExpandedChange = { expanded = it }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    ListItem(modifier.clickable(onClick = onClick),
        icon = {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = contentColor
            )
        },
        text = {
            Text(
                text = stringResource(R.string.shopping_add_item_button),
                color = contentColor
            )
        }
    )
}

@Preview
@Composable
private fun PreviewEmptyContent() {
    AppTheme {
        Surface {
            EmptyContent(ShoppingScreenUi.preview())
        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    AppTheme {
        Surface {
            Content(ShoppingScreenUi.preview())
        }
    }
}