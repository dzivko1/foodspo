package altline.foodspo.ui.recipe.component

import altline.foodspo.R
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.ui.core.component.GeneralImage
import altline.foodspo.ui.core.component.SaveIcon
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

data class RecipeCardUi(
    val id: Long,
    val title: String,
    val image: ImageSrc,
    val author: String?,
    val isSaved: Boolean
) {
    companion object {
        val PREVIEW = RecipeCardUi(
            id = 0,
            title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
            image = PlaceholderImages.recipe,
            author = "Maplewood Road",
            isSaved = false
        )
    }
}

@Composable
fun RecipeCard(
    recipe: RecipeCardUi,
    onRecipeClick: () -> Unit,
    onAddToShoppingList: () -> Unit,
    onSavedChange: (Boolean) -> Unit
) {
    Card(
        Modifier.clickable(onClick = onRecipeClick)
    ) {
        Column {
            GeneralImage(
                image = recipe.image,
                contentDescription = null,
                Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder_recipe)
            )
            Column(
                Modifier.padding(AppTheme.spaces.xl)
            ) {
                Text(
                    text = recipe.title,
                    style = AppTheme.typography.subtitle1
                )
                recipe.author?.let {
                    Text(
                        text = "by $it",
                        style = AppTheme.typography.caption,
                        color = modifiedColor(alpha = ContentAlpha.medium)
                    )
                }
            }
            Row(Modifier.align(End)) {
                IconButton(onClick = onAddToShoppingList) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = stringResource(R.string.content_desc_add_ingredients_to_shopping_list),
                        tint = modifiedColor(alpha = ContentAlpha.medium)
                    )
                }
                IconToggleButton(checked = recipe.isSaved, onCheckedChange = onSavedChange) {
                    SaveIcon(recipe.isSaved)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRecipeCard() {
    AppTheme {
        RecipeCard(
            recipe = RecipeCardUi.PREVIEW,
            onRecipeClick = {},
            onAddToShoppingList = {},
            onSavedChange = {}
        )
    }
}