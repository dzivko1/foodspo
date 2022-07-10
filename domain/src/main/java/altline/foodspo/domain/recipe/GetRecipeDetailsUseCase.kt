package altline.foodspo.domain.recipe

import altline.foodspo.data.di.IODispatcher
import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(recipeId: Long): Flow<Recipe> {
        return recipeRepository.getRecipeDetails(recipeId)
            .flowOn(dispatcher)
    }
}