package altline.foodspo.data.core.model

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

interface ImageSrc

class DrawableImageSrc(@DrawableRes val drawableRes: Int) : ImageSrc
class PathImageSrc(val path: String) : ImageSrc
class BitmapImageSrc(val bitmap: Bitmap) : ImageSrc

fun ImageSrc(@DrawableRes drawableRes: Int): ImageSrc = DrawableImageSrc(drawableRes)
fun ImageSrc(path: String): ImageSrc = PathImageSrc(path)
fun ImageSrc(bitmap: Bitmap): ImageSrc = BitmapImageSrc(bitmap)