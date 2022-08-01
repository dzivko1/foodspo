package altline.foodspo.error

import altline.foodspo.data.error.AppException
import altline.foodspo.data.error.UnknownException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import timber.log.Timber

fun <T> Result<T>.onError(action: (AppException) -> Unit): Result<T> {
    return onFailure { throwable ->
        action(mapError(throwable))
    }
}

fun <T> Flow<T>.onError(action: suspend FlowCollector<T>.(AppException) -> Unit): Flow<T> {
    return catch { throwable ->
        action(this, mapError(throwable))
    }
}

private fun mapError(throwable: Throwable): AppException {
    return if (throwable is Exception) {
        Timber.e(throwable, "Caught app exception.")
        
        throwable as? AppException ?: UnknownException(throwable)
        
    } else throw throwable
}