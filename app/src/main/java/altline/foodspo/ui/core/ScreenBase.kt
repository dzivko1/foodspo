package altline.foodspo.ui.core

import altline.foodspo.data.error.AppException
import altline.foodspo.ui.core.component.InfoPanel
import altline.foodspo.ui.core.component.PageLoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun <UiData> ScreenBase(
    viewModel: ViewModelBase<UiData>,
    errorScreen: @Composable (AppException) -> Unit = { InfoPanel(it, retryAction = viewModel::loadData) },
    content: @Composable (UiData) -> Unit
) {
    val scaffoldState = LocalScaffoldState.current
    val navController = LocalNavController.current

    with(viewModel.uiState) {
        if (navEvent != null) {
            navEvent.navigate(navController)
            viewModel.onNavEventConsumed()
            return
        }

        if (loading) PageLoadingIndicator()

        if (snackbar != null) {
            LaunchedEffect(scaffoldState.snackbarHostState, snackbar) {
                scaffoldState.snackbarHostState.showSnackbar(snackbar.message, snackbar.actionLabel, snackbar.duration)
                viewModel.onSnackbarConsumed()
            }
        }

        if (error != null) errorScreen(error)
        else if (data != null) content(data)
    }
}