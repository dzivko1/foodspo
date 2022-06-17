package altline.foodspo.domain.recipe

import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetRandomRecipesUseCase(
    private val recipeRepository: RecipeRepository,
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    suspend operator fun invoke(): Flow<List<Recipe>> {
        return recipeRepository.getRandomRecipes().flowOn(dispatcher)
    }
}