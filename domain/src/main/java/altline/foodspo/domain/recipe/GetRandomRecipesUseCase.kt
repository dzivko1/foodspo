package altline.foodspo.domain.recipe

import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(count: Int): List<Recipe> {
        return recipeRepository.getRandomRecipes(count)
    }
}