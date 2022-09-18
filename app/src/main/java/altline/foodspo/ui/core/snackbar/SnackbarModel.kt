package altline.foodspo.ui.core.snackbar

import androidx.compose.material.SnackbarDuration

data class SnackbarModel(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short
)
