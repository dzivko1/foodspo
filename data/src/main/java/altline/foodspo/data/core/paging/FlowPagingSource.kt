package altline.foodspo.data.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber
import java.lang.Integer.max

class FlowPagingSource<T : Any>(
    private val pagingAccessor: PagingAccessor<T>,
    private val configPageSize: Int
) : PagingSource<Int, T>() {

    // See https://stackoverflow.com/a/71810010/6640693 for an explanation of many problems that were encountered here
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        var pageIndex = params.key ?: 0
        var loadSize = configPageSize
        if (params is LoadParams.Refresh) {
            loadSize *= 3
            pageIndex = max(0, pageIndex - 1)
        }
        val startIndex = pageIndex * configPageSize

        return try {
            val data = pagingAccessor.load(startIndex, loadSize)

            val nextKey =
                if (data.size < configPageSize) null
                else pageIndex + (loadSize / configPageSize)
            val prevKey =
                if (pageIndex == 0) null
                else pageIndex - 1

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey,
                itemsBefore = startIndex
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