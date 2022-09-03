package altline.foodspo.domain.recipe

import altline.foodspo.data.recipe.RecipeRepository
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
   private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: String) {
        recipeRepository.deleteRecipe(recipeId)
    }
}