package altline.foodspo.data.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class FlowPagingSource<T : Any>(
    dataProvider: (loadTrigger: Flow<Pair<Int, Int>>) -> Flow<List<T>>,
    coroutineScope: CoroutineScope
) : PagingSource<Int, T>() {
    
    private val loadTrigger = MutableStateFlow(Pair(0, -1))
    private var loadTriggerResult: CompletableDeferred<List<T>>? = null
    
    init {
        coroutineScope.launch {
            dataProvider(loadTrigger).catch { e ->
                loadTriggerResult?.completeExceptionally(e)
            }.collect {
                loadTriggerResult?.complete(it)
            }
        }
    }
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageSize = params.loadSize
        val startAfterIndex = params.key ?: -1
        
        loadTriggerResult = CompletableDeferred()
        loadTrigger.value = Pair(pageSize, startAfterIndex)
        try {
            loadTriggerResult!!.await().let { data ->
                loadTriggerResult = null
                return LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = if (data.isEmpty()) null else startAfterIndex + data.size
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Exception caught in paging source.")
            return LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }
}