package altline.foodspo.ui.core.navigation

import altline.foodspo.ui.core.LocalNavController
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Observer

/**
 * Observes the saved state of the current backstack entry for the specified [resultKey] and
 * executes [onReceive] for the received value. Child screens can put values in the saved state
 * under [resultKey] and pop the backstack. [onReceive] will be triggered as a result.
 */
@SuppressLint("UnrememberedMutableState")
@Composable
fun <T> ExpectNavResult(
    resultKey: String,
    onReceive: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalNavController.current

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val savedState = savedStateHandle?.getLiveData<T>(resultKey)

    DisposableEffect(resultKey) {
        val observer = Observer<T> {
            onReceive(it)
            savedStateHandle?.remove<T>(resultKey)
        }
        savedState?.observe(lifecycleOwner, observer)
        onDispose { savedState?.removeObserver(observer) }
    }
}