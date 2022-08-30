package altline.foodspo.domain.recipe

import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.model.Instruction
import javax.inject.Inject

class AnalyzeInstructionsUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(instructions: String): List<Instruction> {
        return recipeRepository.analyzeInstructions(instructions)
    }
}