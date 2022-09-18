package altline.foodspo.ui.meal.component

import altline.foodspo.R
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.ui.core.component.GeneralImage
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.ProvideContentColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class MealUi(
    val mealId: String,
    val recipeId: String,
    val title: String,
    val image: ImageSrc,
    val onContentClick: (id: String) -> Unit,
    val onRemoveClick: (id: String) -> Unit
) {
    companion object {
        @Composable
        fun preview() = MealUi(
            mealId = "",
            recipeId = "",
            title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
            image = PlaceholderImages.recipe,
            onContentClick = {},
            onRemoveClick = {}
        )
    }
}

@Composable
fun Meal(data: MealUi) {
    Box(Modifier.clickable { data.onContentClick(data.recipeId) }) {
        GeneralImage(
            data.image,
            contentDescription = null,
            Modifier
                .fillMaxWidth()
                .requiredHeight(240.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.7f)),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideContentColor(Color.White) {
                Text(
                    text = data.title,
                    Modifier
                        .weight(1f)
                        .padding(AppTheme.spaces.large),
                    style = AppTheme.typography.body2
                )
                IconButton(onClick = { data.onRemoveClick(data.mealId) }) {
                    Icon(
                        Icons.Outlined.RemoveCircleOutline,
                        contentDescription = stringResource(R.string.content_desc_remove_recipe_from_plan)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMeal() {
    AppTheme {
        Surface {
            Meal(MealUi.preview())
        }
    }
}