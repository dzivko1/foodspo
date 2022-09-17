package altline.foodspo.data.recipe

import altline.foodspo.data.CUSTOM_RECIPE_ID_PREFIX
import altline.foodspo.data.core.FirebaseDataSource
import altline.foodspo.data.core.RecipeApiDataSource
import altline.foodspo.data.core.paging.PageLoadTrigger
import altline.foodspo.data.core.paging.PagingAccessor
import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.meal.model.Meal
import altline.foodspo.data.recipe.mapper.InstructionMapper
import altline.foodspo.data.recipe.mapper.RecipeMapper
import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.data.recipe.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

internal class RecipeRepositoryImpl @Inject constructor(
    private val apiDataSource: RecipeApiDataSource,
    private val firebaseDataSource: FirebaseDataSource,
    private val mapRecipe: RecipeMapper,
    private val mapInstruction: InstructionMapper,
    private val mapException: ExceptionMapper
) : RecipeRepository {

    override suspend fun searchRecipes(query: String, page: Int, loadSize: Int): List<Recipe> {
        return mapException {
            apiDataSource.searchRecipes(query, page * loadSize, loadSize).map {
                val saved = firebaseDataSource.isRecipeSaved(it.id)
                mapRecipe(it, saved)
            }
        }
    }

    override fun getRandomRecipesPaged(
        loadTrigger: PageLoadTrigger,
        coroutineScope: CoroutineScope
    ): PagingAccessor<Recipe> {
        val flow = mapException.forFlow(
            apiDataSource.getRandomRecipesPaged(loadTrigger).map { responses ->
                responses.map {
                    val saved = firebaseDataSource.isRecipeSaved(it.id)
                    mapRecipe(it, saved)
                }
            }
        )
        return PagingAccessor(flow, loadTrigger, coroutineScope)
    }

    override suspend fun getRecipeDetails(recipeId: String): Recipe? {
        return mapException {
            if (recipeId.startsWith(CUSTOM_RECIPE_ID_PREFIX)) {
                firebaseDataSource.getMyRecipeDetails(recipeId)
            } else {
                apiDataSource.getRecipeDetails(recipeId).let {
                    val saved = firebaseDataSource.isRecipeSaved(it.id)
                    mapRecipe(it, saved)
                }
            }
        }
    }

    override suspend fun getRecipeDetailsBulk(recipeIds: List<String>): List<Recipe> {
        return mapException {
            apiDataSource.getRecipeDetailsBulk(recipeIds).map {
                val saved = firebaseDataSource.isRecipeSaved(it.id)
                mapRecipe(it, saved)
            }
        }
    }

    override fun getMyRecipesPaged(
        loadTrigger: PageLoadTrigger,
        coroutineScope: CoroutineScope
    ): PagingAccessor<Recipe> {
        val flow = mapException.forFlow(
            firebaseDataSource.getMyRecipesPaged(loadTrigger)
        )
        return PagingAccessor(flow, loadTrigger, coroutineScope)
    }

    override fun getSavedRecipesPaged(
        loadTrigger: PageLoadTrigger,
        coroutineScope: CoroutineScope
    ): PagingAccessor<Recipe> {
        val flow = mapException.forFlow(
            firebaseDataSource.getSavedRecipeIdsPaged(loadTrigger).map { ids ->
                if (ids.isNotEmpty()) {
                    apiDataSource.getRecipeDetailsBulk(ids).map {
                        mapRecipe(it, true)
                    }
                } else emptyList()
            }
        )
        return PagingAccessor(flow, loadTrigger, coroutineScope)
    }

    override suspend fun storeCustomRecipe(recipe: Recipe): String {
        return mapException {
            firebaseDataSource.storeCustomRecipe(recipe)
        }
    }

    override suspend fun deleteRecipe(recipeId: String) {
        mapException {
            firebaseDataSource.deleteRecipe(recipeId)
        }
    }

    override suspend fun saveRecipe(recipeId: String, save: Boolean) {
        mapException {
            firebaseDataSource.saveRecipe(recipeId, save)
        }
    }

    override suspend fun analyzeInstructions(instructions: String): List<Instruction> {
        return mapException {
            if (instructions.isNotEmpty()) {
                apiDataSource.analyzeInstructions(instructions)
                    .parsedInstructions.firstOrNull()
                    ?.steps?.map(mapInstruction::invoke)
                    ?: emptyList()
            } else emptyList()
        }
    }

    override suspend fun createMeal(recipeId: String): Meal? {
        return getRecipeDetails(recipeId)?.let { recipe ->
            Meal(
                id = UUID.randomUUID().toString(),
                recipeId = recipeId,
                recipeTitle = recipe.title,
                recipeImage = recipe.image
            )
        }
    }
}