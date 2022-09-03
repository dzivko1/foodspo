package altline.foodspo.ui.recipe

import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.ingredient.model.Ingredient
import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.data.recipe.model.Recipe
import altline.foodspo.ui.core.component.ListEditorUi
import altline.foodspo.ui.ingredient.IngredientUiMapper
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.recipe.component.RecipeCardUi
import altline.foodspo.ui.screen.recipeDetails.RecipeDetailsScreenUi
import altline.foodspo.ui.screen.recipeEditor.RecipeEditorScreenUi
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class RecipeUiMapper @Inject constructor(
    private val ingredientUiMapper: IngredientUiMapper
) {

    fun toRecipeCard(
        raw: Recipe,
        enableSaveChange: Boolean,
        onContentClick: () -> Unit,
        onAddToShoppingList: () -> Unit,
        onSavedChange: (Boolean) -> Unit = {}
    ) = RecipeCardUi(
        id = raw.id,
        title = raw.title,
        image = raw.image ?: PlaceholderImages.recipe,
        author = raw.sourceName,
        isSaved = if (enableSaveChange) raw.isSaved else null,
        onContentClick = onContentClick,
        onAddToShoppingList = onAddToShoppingList,
        onSavedChange = onSavedChange
    )

    fun toRecipeDetailsUi(raw: Recipe) = RecipeDetailsScreenUi(
        id = raw.id,
        isOwnedByUser = raw.isOwnedByUser,
        image = raw.image ?: PlaceholderImages.recipe,
        title = raw.title,
        author = raw.sourceName,
        creditsText = raw.creditsText,
        servings = raw.servings,
        readyInMinutes = raw.readyInMinutes,
        ingredients = raw.ingredients.map(ingredientUiMapper::toListItemUi),
        instructions = raw.instructions,
        summary = raw.summary?.takeIf { it.isNotEmpty() },
        sourceUrl = raw.sourceUrl,
        spoonacularSourceUrl = raw.spoonacularSourceUrl,
        isSaved = raw.isSaved
    )

    fun toRecipeEditorUi(
        raw: Recipe?,
        onTitleChange: (String) -> Unit,
        onNewImage: (ImageSrc) -> Unit,
        onServingsChange: (String) -> Unit,
        onReadyInMinutesChange: (String) -> Unit,
        onInstructionsChange: (String) -> Unit,
        onSummaryChange: (String) -> Unit,
        onEditingIngredientChange: (Int, Boolean) -> Unit,
        onIngredientEdit: (Int, String) -> Unit,
        onIngredientAdd: () -> Unit,
        onIngredientRemove: (Int) -> Unit,
        onSaveRecipe: () -> Unit
    ) = RecipeEditorScreenUi(
        recipeId = raw?.id,
        title = raw?.title ?: "",
        image = raw?.image ?: PlaceholderImages.imageSlot,
        servings = raw?.servings?.toString() ?: "",
        readyInMinutes = raw?.readyInMinutes?.toString() ?: "",
        ingredientEditorUi = ListEditorUi(
            items = raw?.ingredients?.map { it.rawText } ?: emptyList(),
            editingItemIndex = null,
            onEditingItemChange = onEditingIngredientChange,
            onItemEdit = onIngredientEdit,
            onItemAdd = onIngredientAdd,
            onItemRemove = onIngredientRemove
        ),
        instructions = raw?.rawInstructions ?: "",
        summary = raw?.summary ?: "",
        onTitleChange = onTitleChange,
        onNewImage = onNewImage,
        onServingsChange = onServingsChange,
        onReadyInMinutesChange = onReadyInMinutesChange,
        onInstructionsChange = onInstructionsChange,
        onSummaryChange = onSummaryChange,
        onSaveRecipe = onSaveRecipe
    )

    fun fromRecipeEditorUi(
        ui: RecipeEditorScreenUi,
        parsedInstructions: List<Instruction>,
        parsedIngredients: List<Ingredient>
    ) = Recipe(
        id = ui.recipeId ?: "",
        title = ui.title,
        image = ui.image,
        sourceName = FirebaseAuth.getInstance().currentUser!!.displayName,
        creditsText = FirebaseAuth.getInstance().currentUser!!.displayName,
        sourceUrl = null,
        spoonacularSourceUrl = null,
        servings = ui.servings.toIntOrNull(),
        readyInMinutes = ui.readyInMinutes.toIntOrNull(),
        instructions = parsedInstructions,
        summary = ui.summary,
        ingredients = parsedIngredients,
        additionTime = null,
        isSaved = true
    )
}