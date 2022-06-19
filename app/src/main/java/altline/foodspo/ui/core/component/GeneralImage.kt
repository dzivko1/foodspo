package altline.foodspo.ui.core.component

import altline.foodspo.data.util.ImageSrc
import altline.foodspo.data.util.ImageSrc.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun GeneralImage(
    image: ImageSrc,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Painter? = null
) {
    when (image) {
        is Vector -> {
            Image(
                imageVector = image.imageVector,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale
            )
        }
        is Bitmap -> {
            Image(
                bitmap = image.imageBitmap,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale
            )
        }
        is Drawable -> {
            Image(
                painter = painterResource(image.drawableRes),
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale
            )
        }
        is Path -> {
            AsyncImage(
                model = image.path,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                placeholder = placeholder
            )
        }
    }
}