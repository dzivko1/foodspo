package altline.foodspo.data.core.paging

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

fun Query.paginate(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<DocumentSnapshot>> {
    return loadTrigger.map { (pageSize, lastLoadedIndex) ->
        val documents = mutableListOf<DocumentSnapshot>()
        val startIndex = lastLoadedIndex + 1
        val endIndex = startIndex + pageSize
        val behind = documents.size - startIndex
        val amountToFetch = pageSize - behind
        
        if (amountToFetch > 0) {
            if (documents.isNotEmpty()) startAfter(documents.last())
            limit(amountToFetch.toLong())
            documents += get().await().documents
        }
        
        documents.subList(startIndex, endIndex.coerceAtMost(documents.size))
    }
}