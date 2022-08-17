package altline.foodspo.data.core.paging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PageLoadTrigger {
    private val stateFlow = MutableSharedFlow<PageLoadParams>()
    val flow: Flow<PageLoadParams> = stateFlow.asSharedFlow()

    suspend fun trigger(loadParams: PageLoadParams) {
        stateFlow.emit(loadParams)
    }
}