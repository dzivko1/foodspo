package altline.foodspo.data.core.paging

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Gives access to paged data in a general way. This class wraps a [PageLoadTrigger] and a flow of
 * loaded data. The trigger and the flow must be connected externally, otherwise a triggering will
 * not have a result. When properly connected, calling the [load] function triggers a page load,
 * giving a list of results obtained from the wrapped [flow].
 */
class PagingAccessor<T>(
    private val flow: Flow<List<T>>,
    private val loadTrigger: PageLoadTrigger,
    coroutineScope: CoroutineScope
) {
    private var loadTriggerResult = CompletableDeferred<List<T>>()

    init {
        coroutineScope.launch {
            flow.catch { e ->
                loadTriggerResult.completeExceptionally(e)
            }.collect {
                loadTriggerResult.complete(it)
            }
        }
    }

    /**
     * Triggers the [loadTrigger] with the given [loadParams] and returns the first collected result
     * from the [flow].
     */
    suspend fun load(loadParams: PageLoadParams): List<T> {
        loadTriggerResult = CompletableDeferred()
        loadTrigger.trigger(loadParams)
        return loadTriggerResult.await()
    }
}