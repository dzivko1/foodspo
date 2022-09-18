package altline.foodspo.ui.core.dialog

sealed interface DialogUi {

    data class Confirmation(
        val title: String,
        val message: String? = null,
        val onConfirm: () -> Unit,
        val onDismiss: () -> Unit,
        val dismissOnConfirm: Boolean = true
    ) : DialogUi

}