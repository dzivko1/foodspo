package altline.foodspo.data.core.paging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * A trigger for loading paginated data. Triggering emits a given [PageLoadParams] object to a
 * wrapped [kotlinx.coroutines.flow.SharedFlow]. The exposed flow can be mapped to loaded data.
 */
class PageLoadTrigger {
    private val sharedFlow = MutableSharedFlow<PageLoadParams>()
    val flow: Flow<PageLoadParams> = sharedFlow.asSharedFlow()

    /**
     * Emits the given [loadParams] to the the wrapped flow.
     */
    suspend fun trigger(loadParams: PageLoadParams) {
        sharedFlow.emit(loadParams)
    }
}