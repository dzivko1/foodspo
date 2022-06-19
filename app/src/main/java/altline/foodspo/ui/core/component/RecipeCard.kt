package altline.foodspo.ui.core.component

import altline.foodspo.R
import altline.foodspo.data.util.ImageSrc
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.ui.theme.onSurfaceMedium
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

data class RecipeCardUi(
    val title: String,
    val image: ImageSrc,
    val author: String?,
    val isSaved: Boolean
)

@Composable
fun RecipeCard(
    recipe: RecipeCardUi,
    onOpenRecipe: () -> Unit,
    onAddToCart: () -> Unit,
    onSave: () -> Unit
) {
    Card(
        Modifier.clickable(onClick = onOpenRecipe)
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
                        color = AppTheme.colors.onSurfaceMedium
                    )
                }
            }
            Row(Modifier.align(End)) {
                IconButton(onClick = onAddToCart) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = stringResource(R.string.content_desc_add_ingredients_to_cart),
                        tint = AppTheme.colors.onSurfaceMedium
                    )
                }
                IconButton(onClick = onSave) {
                    Icon(
                        imageVector = if (recipe.isSaved) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.content_desc_save_recipe),
                        tint = AppTheme.colors.onSurfaceMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecipeCard() {
    AppTheme {
        RecipeCard(
            recipe = RecipeCardUi(
                title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
                image = PlaceholderImages.recipe,
                author = "Maplewood Road",
                isSaved = false
            ),
            onOpenRecipe = {},
            onAddToCart = {},
            onSave = {}
        )
    }
}