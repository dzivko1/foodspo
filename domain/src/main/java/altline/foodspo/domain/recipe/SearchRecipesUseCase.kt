package altline.foodspo.domain.recipe

import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Recipe
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(query: String, page: Int, loadSize: Int): List<Recipe> {
        return recipeRepository.searchRecipes(query, page, loadSize)
    }
}