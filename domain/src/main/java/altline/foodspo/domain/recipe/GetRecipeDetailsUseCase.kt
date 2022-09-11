package altline.foodspo.domain.recipe

import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: String): Recipe? {
        return recipeRepository.getRecipeDetails(recipeId)
    }
}