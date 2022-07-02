package altline.foodspo.data.recipe

import altline.foodspo.data.recipe.model.Recipe
import androidx.paging.PagingSource
import androidx.paging.PagingState

class RecipePagingSource(
    private val recipeProvider: suspend (page: Int, loadSize: Int) -> List<Recipe>
) : PagingSource<Int, Recipe>() {
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val nextPageNumber = params.key ?: 1
            val loadSize = params.loadSize
            val recipes = recipeProvider.invoke(nextPageNumber, loadSize)
            LoadResult.Page(
                data = recipes,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition
    }
}