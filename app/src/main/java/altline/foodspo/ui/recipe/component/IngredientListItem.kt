package altline.foodspo.ui.recipe.component

import altline.foodspo.R
import altline.foodspo.data.ingredient.model.Measure
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

data class IngredientListItemUi(
    val id: Long,
    val name: String,
    val measure: Measure?
)

@Composable
fun IngredientListItem(
    ingredient: IngredientListItemUi
) {
    Row(
        Modifier
            .padding(start = AppTheme.spaces.xl)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("${ingredient.name} | ${ingredient.measure}")
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
        IngredientListItem(
            IngredientListItemUi(
                id = 0,
                name = "Bread",
                measure = Measure(
                    amount = 2.0,
                    unitLong = "slices",
                    unitShort = "slice"
                )
            )
        )
    }
}