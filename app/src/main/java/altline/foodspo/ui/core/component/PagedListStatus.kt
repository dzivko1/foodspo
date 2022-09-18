package altline.foodspo.ui.core.component

import altline.foodspo.R
import altline.foodspo.util.anyError
import altline.foodspo.util.isAnyLoading
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> PagedListStatus(
    items: LazyPagingItems<T>,
    emptyContent: @Composable (() -> Unit)? = null
) {
    items.loadState.anyError?.let {
        Text(
            stringResource(
                R.string.page_loading_error_format,
                it.localizedMessage ?: "unknown"
            )
        )
    }

    if (items.loadState.isAnyLoading) {
        LoadingSpinner()
    } else if (emptyContent != null && items.itemCount == 0) {
        emptyContent()
    }
}