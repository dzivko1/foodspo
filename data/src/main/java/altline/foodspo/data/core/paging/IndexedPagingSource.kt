package altline.foodspo.data.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber

class IndexedPagingSource<T : Any>(
    private val dataProvider: suspend (page: Int, loadSize: Int) -> List<T>
) : PagingSource<Int, T>() {
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPageNumber = params.key ?: 1
            val loadSize = params.loadSize
            val data = dataProvider.invoke(nextPageNumber, loadSize)
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            Timber.e(e, "Exception caught in paging source.")
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }
}