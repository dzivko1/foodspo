package altline.foodspo.util

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

// Method for keyboard detection kindly provided here:
// https://stackoverflow.com/a/69533584/6640693
@Composable
fun OnKeyboardStateChange(
    action: @Composable (Boolean) -> Unit
) {
    val view = LocalView.current
    var keyboardState by remember { mutableStateOf(detectKeyboardOpen(view)) }
    var stateChanged by remember { mutableStateOf(false) }

    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            keyboardState = detectKeyboardOpen(view).also {
                stateChanged = it != keyboardState
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    if (stateChanged) {
        action(keyboardState)
        stateChanged = false
    }
}

/**
 * Detects whether the keyboard is open by measuring and comparing root view height and the visible
 * display frame. The required [composeView] can be retrieved in a composable function using [LocalView].
 */
fun detectKeyboardOpen(composeView: View): Boolean {
    val displayRect = Rect()
    composeView.getWindowVisibleDisplayFrame(displayRect)
    val screenHeight = composeView.rootView.height
    val keyboardHeight = screenHeight - displayRect.bottom
    return keyboardHeight > (screenHeight * 0.15)
}