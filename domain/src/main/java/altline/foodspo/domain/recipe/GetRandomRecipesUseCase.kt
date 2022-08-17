package altline.foodspo.domain.recipe

import altline.foodspo.data.core.paging.PageLoadTrigger
import altline.foodspo.data.core.paging.PagingAccessor
import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(coroutineScope: CoroutineScope): PagingAccessor<Recipe> {
        return recipeRepository.getRandomRecipesPaged(PageLoadTrigger(), coroutineScope)
    }
}