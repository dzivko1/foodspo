package altline.foodspo.ui.core.dialog

import androidx.compose.runtime.Composable

@Composable
fun GenericDialog(dialogUi: DialogUi) {
    when (dialogUi) {
        is DialogUi.Confirmation -> ConfirmationDialog(dialogUi)
    }
}