package altline.foodspo.ui.recipe.component

import altline.foodspo.R
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.ui.core.component.GeneralImage
import altline.foodspo.ui.core.component.SaveButton
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class RecipeCardUi(
    val id: String,
    val title: String,
    val image: ImageSrc,
    val author: String?,
    val isSaved: Boolean?,
    val onContentClick: () -> Unit,
    val onAddToShoppingList: () -> Unit,
    val onSavedChange: (Boolean) -> Unit = {},
    val onPick: (() -> Unit)?
) {
    companion object {
        val PREVIEW = RecipeCardUi(
            id = "",
            title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
            image = PlaceholderImages.recipe,
            author = "Maplewood Road",
            isSaved = false,
            onContentClick = {},
            onAddToShoppingList = {},
            onSavedChange = {},
            onPick = null
        )
    }
}

@Composable
fun RecipeCard(
    data: RecipeCardUi
) {
    Card(
        Modifier.clickable(onClick = data.onPick ?: data.onContentClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GeneralImage(
                image = data.image,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .requiredHeight(240.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder_recipe)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spaces.xl)
            ) {
                Text(
                    text = data.title,
                    style = AppTheme.typography.subtitle1
                )
                data.author?.let {
                    Text(
                        text = "by $it",
                        style = AppTheme.typography.caption,
                        color = modifiedColor(alpha = ContentAlpha.medium)
                    )
                }
            }
            if (data.onPick != null) {
                Text(
                    text = stringResource(R.string.action_pick).uppercase(),
                    Modifier.padding(AppTheme.spaces.large),
                    color = AppTheme.colors.secondary,
                    style = AppTheme.typography.button
                )
            } else {
                Row(Modifier.align(End)) {
                    IconButton(onClick = data.onAddToShoppingList) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = stringResource(R.string.content_desc_add_ingredients_to_shopping_list),
                            tint = modifiedColor(alpha = ContentAlpha.medium)
                        )
                    }
                    if (data.isSaved != null) {
                        SaveButton(data.isSaved, data.onSavedChange)
                    }
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
            data = RecipeCardUi.PREVIEW
        )
    }
}

@Preview
@Composable
private fun PreviewRecipeCard_PickMode() {
    AppTheme {
        RecipeCard(
            data = RecipeCardUi.PREVIEW.copy(onPick = {})
        )
    }
}