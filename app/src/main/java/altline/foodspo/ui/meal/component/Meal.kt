package altline.foodspo.ui.meal.component

import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

data class MealUi(
    val id: String,
    val title: String,
    val image: ImageSrc,
    val onContentClick: (id: String) -> Unit,
    val onRemoveClick: (id: String) -> Unit
) {
    companion object {
        @Composable
        fun preview() = MealUi(
            id = "",
            title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
            image = PlaceholderImages.recipe,
            onContentClick = {},
            onRemoveClick = {}
        )
    }
}

@Composable
fun Meal(data: MealUi) {

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