package altline.foodspo.ui.ingredient.component

import altline.foodspo.R
import altline.foodspo.data.ingredient.model.Measure
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

data class IngredientListItemUi(
    val id: String,
    val name: String,
    val measure: Measure?
) {
    companion object {
        val PREVIEW = IngredientListItemUi(
            id = "",
            name = "Bread",
            measure = Measure(
                amount = 2.0,
                unitLong = "slices",
                unitShort = "slice"
            )
        )
    }
}

@Composable
fun IngredientListItem(
    ingredient: IngredientListItemUi
) {
    Row(
        Modifier.padding(start = AppTheme.spaces.xl),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${ingredient.name} | ${ingredient.measure}",
            Modifier.weight(1f),
            style = AppTheme.typography.body2
        )
        IconButton(onClick = {}) {
            Icon(
                Icons.Default.AddShoppingCart,
                contentDescription = stringResource(R.string.content_desc_add_to_shopping_list),
                tint = modifiedColor(alpha = ContentAlpha.medium)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewIngredientListItem() {
    AppTheme {
        Surface {
            IngredientListItem(IngredientListItemUi.PREVIEW)
        }
    }
}