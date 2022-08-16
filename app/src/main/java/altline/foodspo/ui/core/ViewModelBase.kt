package altline.foodspo.ui.core

import altline.foodspo.data.error.AppException
import altline.foodspo.error.onError
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

    protected suspend fun <T> runAction(block: suspend () -> T): Result<T> {
        setLoading(true)
        return kotlin.runCatching {
            block()
        }.onError {
            setError(it)
        }.also {
            setLoading(false)
        }
    }

    protected fun setUiData(uiData: UiData?) {
        uiState = uiState.copy(data = uiData)
    }

    protected fun setLoading(loading: Boolean) {
        uiState = uiState.copy(loading = loading)
    }

    protected fun setError(error: AppException?) {
        uiState = uiState.copy(error = error)
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

    open fun onFabClick() {
        /* no-op */
    }
}