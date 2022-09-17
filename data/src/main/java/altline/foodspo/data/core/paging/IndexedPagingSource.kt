package altline.foodspo.data.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber

/**
 * An implementation of [PagingSource] that works with page indices and load sizes. It obtains data
 * from the specified [dataProvider] function.
 */
class IndexedPagingSource<T : Any>(
    private val configPageSize: Int,
    private val dataProvider: suspend (page: Int, loadSize: Int) -> List<T>,
    private val allowNegativePages: Boolean = false,
) : PagingSource<Int, T>() {

    // See https://stackoverflow.com/a/71810010/6640693 for an explanation of many problems that were encountered here
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        var pageIndex = params.key ?: 0
        var loadSize = configPageSize
        if (params is LoadParams.Refresh) {
            loadSize *= 3
            if (allowNegativePages) pageIndex -= 1
            else pageIndex = (pageIndex - 1).coerceAtLeast(0)
        }
        val startIndex = pageIndex * configPageSize

        return try {
            val data = dataProvider(pageIndex, loadSize)

            val nextKey =
                if (data.size < configPageSize) null
                else pageIndex + (loadSize / configPageSize)
            val prevKey =
                if (!allowNegativePages && pageIndex == 0) null
                else pageIndex - 1
            val itemsBefore =
                if (allowNegativePages) LoadResult.Page.COUNT_UNDEFINED
                else startIndex

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey,
                itemsBefore = itemsBefore

            )
        } catch (e: Exception) {
            Timber.e(e, "Exception caught in paging source.")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {
            it / configPageSize
        }
    }
}