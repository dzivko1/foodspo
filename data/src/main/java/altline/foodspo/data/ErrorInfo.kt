package altline.foodspo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import timber.log.Timber

sealed interface ErrorInfo {
    data class Other(val exception: Exception) : ErrorInfo
}

fun <T> Flow<T>.onError(action: suspend FlowCollector<T>.(ErrorInfo) -> Unit): Flow<T> {
    return catch { throwable ->
        Timber.e(throwable)
        val errorInfo = when (throwable) {
            is Error -> throw throwable
            else -> ErrorInfo.Other(throwable as Exception)
        }
        action(this, errorInfo)
    }
}