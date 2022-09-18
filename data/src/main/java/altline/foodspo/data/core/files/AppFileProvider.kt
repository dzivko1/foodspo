package altline.foodspo.data.core.files

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class AppFileProvider : FileProvider() {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "camera_",
                null,
                directory,
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}