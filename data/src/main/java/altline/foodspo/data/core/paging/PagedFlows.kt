package altline.foodspo.data.core.paging

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/**
 * Returns a new flow that emits values based on the specified [loadTrigger] and [dataSource].
 * The load trigger triggers the load and the data source provides the actual values based on
 * [PageLoadParams] and all previously loaded items.
 *
 * NOTE: The current version might have issues when page loading doesn't start at the first index.
 */
fun <T> pagedFlow(
    loadTrigger: PageLoadTrigger,
    dataSource: suspend (PageLoadParams, loadedItems: List<T>) -> List<T>
): Flow<List<T>> {
    return flow {
        val items = mutableListOf<T>()

        loadTrigger.flow.map { (startIndex, pageSize) ->
            val endIndex = startIndex + pageSize
            val amountToFetch = endIndex - items.size

            if (amountToFetch > 0) {
                items += dataSource(PageLoadParams(startIndex, amountToFetch), items)
            }

            items.subList(startIndex, endIndex.coerceAtMost(items.size))
        }.collect {
            emit(it)
        }
    }
}

/**
 * Paginates a query of [DocumentSnapshot]s. Internally creates a [pagedFlow] with the specified
 * [loadTrigger] and this query as the source of data.
 */
fun Query.paginate(loadTrigger: PageLoadTrigger): Flow<List<DocumentSnapshot>> {
    return pagedFlow(loadTrigger) { loadParams, loadedItems ->
        this.run {
            if (loadedItems.isNotEmpty()) startAfter(loadedItems.last())
            else this
        }.limit(loadParams.pageSize.toLong())
            .get().await().documents
    }
}