package altline.foodspo.ui.core.component

import altline.foodspo.util.anyError
import altline.foodspo.util.isAnyLoading
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> PagedListStatus(
    items: LazyPagingItems<T>,
    emptyContent: @Composable (() -> Unit)? = null
) {
    items.loadState.anyError?.let {
        InfoPanel(it) {
            items.retry()
        }
    }

    if (items.loadState.isAnyLoading) {
        LoadingSpinner()
    } else if (emptyContent != null && items.itemCount == 0) {
        emptyContent()
    }
}