package altline.foodspo.data.core.paging

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

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

    suspend fun load(startIndex: Int, pageSize: Int): List<T> {
        loadTriggerResult = CompletableDeferred()
        loadTrigger.trigger(PageLoadParams(startIndex, pageSize))
        return loadTriggerResult.await()
    }
}