package altline.foodspo.ui.core

import altline.foodspo.data.error.AppException
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.core.snackbar.SnackbarModel

data class UiState<UiData>(
    val loading: Boolean = false,
    val error: AppException? = null,
    val snackbar: SnackbarModel? = null,
    val navEvent: NavigationEvent? = null,
    val data: UiData? = null
)