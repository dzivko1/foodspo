package altline.foodspo.domain.recipe

import altline.foodspo.data.di.IODispatcher
import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSavedRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(loadTrigger: Flow<Pair<Int, Int>>): Flow<List<Recipe>> {
        return recipeRepository.getSavedRecipesPaged(loadTrigger)
            .flowOn(dispatcher)
    }
}