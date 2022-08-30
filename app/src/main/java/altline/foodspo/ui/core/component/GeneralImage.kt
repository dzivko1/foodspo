package altline.foodspo.ui.core.component

import altline.foodspo.data.core.model.BitmapImageSrc
import altline.foodspo.data.core.model.DrawableImageSrc
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.core.model.PathImageSrc
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
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
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = null
) {
    when (image) {
        is BitmapImageSrc -> {
            Image(
                bitmap = image.bitmap.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
        is DrawableImageSrc -> {
            Image(
                painter = painterResource(image.drawableRes),
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
        is PathImageSrc -> {
            AsyncImage(
                model = image.path,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                placeholder = placeholder,
                error = error,
                fallback = fallback
            )
        }
    }
}