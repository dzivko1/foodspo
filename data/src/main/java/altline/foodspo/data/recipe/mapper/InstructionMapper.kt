package altline.foodspo.data.recipe.mapper

import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.data.recipe.model.network.InstructionResponse
import javax.inject.Inject

internal class InstructionMapper @Inject constructor() {

    operator fun invoke(raw: InstructionResponse): Instruction {
        return Instruction(raw.number, raw.text)
    }
}