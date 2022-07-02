package altline.foodspo.domain.recipe

import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe

class GetRandomRecipesUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(count: Int): List<Recipe> {
        return recipeRepository.getRandomRecipes(count)
    }
}