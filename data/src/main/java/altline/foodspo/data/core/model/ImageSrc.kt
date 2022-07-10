package altline.foodspo.data.core.model

import androidx.annotation.DrawableRes

interface ImageSrc

class DrawableImageSrc(@DrawableRes val drawableRes: Int) : ImageSrc
class PathImageSrc(val path: String) : ImageSrc

fun ImageSrc(@DrawableRes drawableRes: Int): ImageSrc = DrawableImageSrc(drawableRes)
fun ImageSrc(path: String): ImageSrc = PathImageSrc(path)