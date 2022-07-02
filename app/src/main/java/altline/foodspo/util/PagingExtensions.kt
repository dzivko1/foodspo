package altline.foodspo.util

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading

val CombinedLoadStates.isAnyLoading: Boolean
    get() = append is Loading || refresh is Loading || prepend is Loading

val CombinedLoadStates.anyError: Throwable?
    get() = when {
        append is Error -> (append as Error).error
        refresh is Error -> (refresh as Error).error
        prepend is Error -> (prepend as Error).error
        else -> null
    }
    