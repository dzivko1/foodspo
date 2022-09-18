package altline.foodspo.ui.core

import altline.foodspo.data.error.AppException
import altline.foodspo.error.AppErrorUiMapper
import altline.foodspo.ui.core.navigation.NavigationEvent
import altline.foodspo.ui.core.snackbar.SnackbarModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

abstract class ViewModelBase<UI> : ViewModel() {

    @Inject
    protected lateinit var errorUiMapper: AppErrorUiMapper

    var uiState by mutableStateOf<UiState<UI>>(UiState())
        protected set

    /**
     * Loads the main screen data. Needs to be called manually, usually from the init block of the
     * implementing class.
     */
    abstract fun loadData()

    /**
     * Executes the given suspending code with a loading indicator.
     */
    protected suspend fun <T> runAction(block: suspend () -> T): Result<T> {
        setLoading(true)
        return kotlin.runCatching {
            block()
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

    protected fun showErrorScreen(error: AppException, retryAction: (() -> Unit)? = null) {
        uiState = uiState.copy(infoScreen = errorUiMapper.toInfoPanel(error, retryAction))
    }

    protected fun showSnackbar(snackbarData: SnackbarModel) {
        uiState = uiState.copy(snackbar = snackbarData)
    }

    protected fun showSnackbar(message: String) {
        uiState = uiState.copy(snackbar = SnackbarModel(message))
    }

    protected fun showErrorSnackbar(error: AppException) {
        showSnackbar(errorUiMapper.toSnackbarMessage(error))
    }

    fun onSnackbarConsumed() {
        uiState = uiState.copy(snackbar = null)
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

    fun onNavEventConsumed() {
        uiState = uiState.copy(navEvent = null)
    }

    protected fun <R : Any> finishWithResult(resultKey: String, value: R) {
        uiState = uiState.copy(screenResult = Pair(resultKey, value))
    }

    fun onScreenResultSent() {
        uiState = uiState.copy(screenResult = null)
    }

    open fun onFabClick() {
        /* no-op */
    }
}