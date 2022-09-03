package altline.foodspo.data.recipe.model

import altline.foodspo.data.CUSTOM_RECIPE_ID_PREFIX
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.ingredient.model.IngredientFirestore
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class Recipe(
    val id: String,
    val title: String,
    val image: ImageSrc?,
    val sourceName: String?,
    val creditsText: String?,
    val sourceUrl: String?,
    val spoonacularSourceUrl: String?,
    val servings: Int?,
    val readyInMinutes: Int?,
    val instructions: List<Instruction>,
    val summary: String?,
    val ingredients: List<Ingredient>,
    val additionTime: Timestamp?,
    val isSaved: Boolean
) {
    @get:Exclude
    val rawInstructions: String
        get() = instructions.joinToString("\n")

    @get:Exclude
    val isOwnedByUser: Boolean
        get() = id.startsWith(CUSTOM_RECIPE_ID_PREFIX)
}

data class Instruction(
    val number: Int,
    val text: String
) {
    override fun toString(): String {
        return "$number. $text"
    }
}

internal data class RecipeFirestore(
    var id: String = "",
    var title: String = "",
    var image: Map<String, Any> = emptyMap(),
    var sourceName: String? = null,
    var creditsText: String? = null,
    var sourceUrl: String? = null,
    var spoonacularSourceUrl: String? = null,
    var servings: Int? = null,
    var readyInMinutes: Int? = null,
    var instructions: List<InstructionFirestore> = emptyList(),
    var summary: String? = null,
    var ingredients: List<IngredientFirestore> = emptyList(),
    var additionTime: Timestamp? = null,
    var isSaved: Boolean = false
) {
    fun toDomainModel() = Recipe(
        id,
        title,
        image["path"]?.let {
            when (it) {
                is String -> ImageSrc(path = it)
                is Long -> ImageSrc(drawableRes = it.toInt())
                else -> null
            }
        },
        sourceName,
        creditsText,
        sourceUrl,
        spoonacularSourceUrl,
        servings,
        readyInMinutes,
        instructions.map { it.toDomainModel() },
        summary,
        ingredients.map { it.toDomainModel() },
        additionTime,
        isSaved
    )
}

internal data class InstructionFirestore(
    var number: Int = 0,
    var text: String = ""
) {
    fun toDomainModel() = Instruction(
        number, text
    )
}