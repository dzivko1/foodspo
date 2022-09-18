package altline.foodspo.ui.core

import altline.foodspo.ui.core.component.InfoPanelUi
import altline.foodspo.ui.core.dialog.DialogUi
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.core.snackbar.SnackbarModel

data class UiState<UiData>(
    val loading: Boolean = false,
    val infoScreen: InfoPanelUi? = null,
    val snackbar: SnackbarModel? = null,
    val dialog: DialogUi? = null,
    val navEvent: NavigationEvent? = null,
    val screenResult: Pair<String, Any>? = null,
    val data: UiData? = null
)