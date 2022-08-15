package altline.foodspo.ui.core

import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.core.snackbar.SnackbarModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class ViewModelBase<UiData> : ViewModel() {

    var uiState by mutableStateOf<UiState<UiData>>(UiState())
        protected set

    abstract fun loadData()

    protected fun setLoading(loading: Boolean) {
        uiState = uiState.copy(loading = loading)
    }

    protected fun showSnackbar(snackbarData: SnackbarModel) {
        uiState = uiState.copy(snackbar = snackbarData)
    }

    protected fun navigateTo(navEvent: NavigationEvent) {
        uiState = uiState.copy(navEvent = navEvent)
    }

    fun onSnackbarConsumed() {
        uiState = uiState.copy(snackbar = null)
    }

    fun onNavEventConsumed() {
        uiState = uiState.copy(navEvent = null)
    }
}