package altline.foodspo.data.core.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector


sealed interface ImageSrc {
    class Vector(val imageVector: ImageVector) : ImageSrc
    class Bitmap(val imageBitmap: ImageBitmap) : ImageSrc
    class Drawable(@DrawableRes val drawableRes: Int) : ImageSrc
    class Path(val path: String) : ImageSrc
}

fun ImageSrc(imageVector: ImageVector): ImageSrc = ImageSrc.Vector(imageVector)
fun ImageSrc(imageBitmap: ImageBitmap): ImageSrc = ImageSrc.Bitmap(imageBitmap)
fun ImageSrc(@DrawableRes drawableRes: Int): ImageSrc = ImageSrc.Drawable(drawableRes)
fun ImageSrc(path: String): ImageSrc = ImageSrc.Path(path)