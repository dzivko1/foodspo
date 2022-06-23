package altline.foodspo.error

import altline.foodspo.data.error.AppException
import altline.foodspo.data.error.UnknownException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import timber.log.Timber

fun <T> Flow<T>.onError(action: suspend FlowCollector<T>.(AppException) -> Unit): Flow<T> {
    return catch { throwable ->
        if (throwable is Exception) {
            Timber.e(throwable, "Caught exception in flow.")
            
            val appException = throwable as? AppException
                ?: UnknownException(throwable)
            
            action(this, appException)
            
        } else throw throwable
    }
}