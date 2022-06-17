package altline.foodspo.ui.core.component

import altline.foodspo.data.util.ImageSrc
import altline.foodspo.data.util.ImageSrc.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun GenericImage(
    image: ImageSrc,
    contentDescription: String?
) {
    when (image) {
        is Vector -> {
            Image(image.imageVector, contentDescription)
        }
        is Bitmap -> {
            Image(image.imageBitmap, contentDescription)
        }
        is Drawable -> {
            Image(painterResource(image.drawableRes), contentDescription)
        }
        is Path -> {
            AsyncImage(image.path, contentDescription)
        }
    }
}