package altline.foodspo.ui.core

import altline.foodspo.data.error.AppException
import altline.foodspo.error.onError
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.core.snackbar.SnackbarModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class ViewModelBase<UI> : ViewModel() {

    var uiState by mutableStateOf<UiState<UI>>(UiState())
        protected set

    /**
     * Loads the main screen data. Needs to be called manually, usually from the init block of the
     * implementing class.
     */
    abstract fun loadData()

    /**
     * Executes the given suspending code with a loading indicator and error handling.
     */
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

    protected fun setUiData(uiData: UI?) {
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
    
    protected fun navigateUp() {
        uiState = uiState.copy(navEvent = NavigationEvent.NavigateUp)
    }

    protected fun navigateBack() {
        uiState = uiState.copy(navEvent = NavigationEvent.NavigateBack)
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