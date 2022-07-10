package altline.foodspo.ui.core.model

import altline.foodspo.data.core.model.ImageSrc
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector

class VectorImageSrc(val imageVector: ImageVector) : ImageSrc
class BitmapImageSrc(val imageBitmap: ImageBitmap) : ImageSrc

fun ImageSrc(imageVector: ImageVector): ImageSrc = VectorImageSrc(imageVector)
fun ImageSrc(imageBitmap: ImageBitmap): ImageSrc = BitmapImageSrc(imageBitmap)