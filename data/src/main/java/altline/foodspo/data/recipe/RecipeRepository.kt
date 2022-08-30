package altline.foodspo.data.recipe

import altline.foodspo.data.core.paging.PageLoadTrigger
import altline.foodspo.data.core.paging.PagingAccessor
import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.CoroutineScope

interface RecipeRepository {

    fun getRandomRecipesPaged(
        loadTrigger: PageLoadTrigger,
        coroutineScope: CoroutineScope
    ): PagingAccessor<Recipe>

    suspend fun getRecipeDetails(recipeId: String): Recipe

    suspend fun getRecipeDetailsBulk(recipeIds: List<String>): List<Recipe>

    fun getMyRecipesPaged(
        loadTrigger: PageLoadTrigger,
        coroutineScope: CoroutineScope
    ): PagingAccessor<Recipe>

    fun getSavedRecipesPaged(
        loadTrigger: PageLoadTrigger,
        coroutineScope: CoroutineScope
    ): PagingAccessor<Recipe>

    suspend fun createRecipe(recipe: Recipe)

    suspend fun saveRecipe(recipeId: String, save: Boolean)

    suspend fun analyzeInstructions(instructions: String): List<Instruction>
}