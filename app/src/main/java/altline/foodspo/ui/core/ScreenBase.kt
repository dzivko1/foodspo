package altline.foodspo.ui.core

import altline.foodspo.ui.core.component.InfoPanel
import altline.foodspo.ui.core.component.PageLoadingIndicator
import altline.foodspo.ui.core.dialog.GenericDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun <UiData> ScreenBase(
    viewModel: ViewModelBase<UiData>,
    topBar: @Composable () -> Unit = { DefaultTopBar() },
    fab: (@Composable () -> Unit)? = null,
    reloadOnResume: Boolean = false,
    content: @Composable (UiData) -> Unit
) {
    val scaffoldState = LocalScaffoldState.current
    val navController = LocalNavController.current

    LocalTopBarSetter.current.invoke(topBar)
    LocalFabSetter.current.invoke(fab)

    if (reloadOnResume) {
        val lifeCycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifeCycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    viewModel.loadData()
                }
            }
            lifeCycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifeCycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    with(viewModel.uiState) {
        if (navEvent != null) {
            navEvent.navigate(navController)
            viewModel.onNavEventConsumed()
            return
        }

        if (loading) PageLoadingIndicator()

        if (snackbar != null) {
            LaunchedEffect(scaffoldState.snackbarHostState, snackbar) {
                scaffoldState.snackbarHostState.showSnackbar(
                    snackbar.message,
                    snackbar.actionLabel,
                    snackbar.duration
                )
                viewModel.onSnackbarConsumed()
            }
        }

        if (dialog != null) GenericDialog(dialog)

        if (screenResult != null) {
            navController.previousBackStackEntry?.savedStateHandle
                ?.set(screenResult.first, screenResult.second)
            navController.popBackStack()
            viewModel.onScreenResultSent()
        }

        if (infoScreen != null) InfoPanel(infoScreen)
        else if (data != null) content(data)
    }
}