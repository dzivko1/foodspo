package altline.foodspo.ui.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Dictionary @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getString(@StringRes resId: Int, vararg formatArgs: Any) =
        context.getString(resId, formatArgs)
}