package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.R
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.ui.core.DefaultTopBar
import altline.foodspo.ui.core.ScreenBase
import altline.foodspo.ui.core.component.AppDropdownMenuItem
import altline.foodspo.ui.core.component.BarDropdownMenu
import altline.foodspo.ui.core.component.GeneralImage
import altline.foodspo.ui.core.component.SaveButton
import altline.foodspo.ui.ingredient.component.IngredientListItem
import altline.foodspo.ui.ingredient.component.IngredientListItemUi
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import altline.foodspo.util.toHtmlAnchor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ireward.htmlcompose.HtmlText

data class RecipeDetailsScreenUi(
    val id: String,
    val isOwnedByUser: Boolean,
    val image: ImageSrc,
    val title: String,
    val author: String?,
    val creditsText: String?,
    val servings: Int?,
    val readyInMinutes: Int?,
    val ingredients: List<IngredientListItemUi>,
    val instructions: List<Instruction>,
    val summary: String?,
    val sourceUrl: String?,
    val spoonacularSourceUrl: String?,
    val isSaved: Boolean
) {
    companion object {
        val PREVIEW = RecipeDetailsScreenUi(
            id = "",
            isOwnedByUser = false,
            image = PlaceholderImages.recipe,
            title = "Bacon-Apple-Pecan Stuffed French Toast",
            author = "Maplewood Road",
            servings = 2,
            readyInMinutes = 30,
            ingredients = listOf(
                IngredientListItemUi.PREVIEW,
                IngredientListItemUi.PREVIEW,
                IngredientListItemUi.PREVIEW
            ),
            instructions = listOf(
                Instruction(1, "Slice bread into 2 pieces."),
                Instruction(2, "Slice an apple into pieces of desired length."),
                Instruction(3, "Put apple on bread.")
            ),
            summary = "A food with bread, apples, and other things.",
            creditsText = "Maplewood Road",
            sourceUrl = "https://maplewoodroad.com/bacon-apple-pecan-stuffed-french-toast/",
            spoonacularSourceUrl = "https://spoonacular.com/bacon-apple-pecan-stuffed-french-toast-1697823",
            isSaved = false
        )
    }
}

@Composable
fun RecipeDetailsScreen(viewModel: RecipeDetailsViewModel = hiltViewModel()) {
    ScreenBase(
        viewModel,
        topBar = {
            TopBar(
                isRecipeUsers = viewModel.uiState.data?.isOwnedByUser ?: false,
                isRecipeSaved = viewModel.uiState.data?.isSaved ?: false,
                onAddToShoppingList = viewModel::onAddRecipeToShoppingListClicked,
                onSave = viewModel::onSaveClicked,
                onEdit = viewModel::onEditClicked,
                onDelete = viewModel::onDeleteClicked
            )
        },
        reloadOnResume = true
    ) {
        Content(it)
    }
}

@Composable
private fun TopBar(
    isRecipeUsers: Boolean,
    isRecipeSaved: Boolean,
    onAddToShoppingList: () -> Unit,
    onSave: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    DefaultTopBar(
        actions = {
            IconButton(onClick = onAddToShoppingList) {
                Icon(
                    Icons.Default.AddShoppingCart,
                    contentDescription = stringResource(R.string.content_desc_add_ingredients_to_shopping_list)
                )
            }
            if (!isRecipeUsers) SaveButton(isRecipeSaved, onSave)
            BarDropdownMenu(
                expanded = menuExpanded,
                onExpandedChange = { menuExpanded = it }
            ) {
                AppDropdownMenuItem(
                    text = stringResource(
                        if (isRecipeUsers) R.string.action_edit
                        else R.string.action_copy_and_edit
                    ),
                    icon = Icons.Outlined.Edit,
                    iconContentDescription = stringResource(R.string.content_desc_edit_recipe),
                    onClick = { onEdit(); menuExpanded = false }
                )
                if (isRecipeUsers) {
                    AppDropdownMenuItem(
                        text = stringResource(R.string.action_delete),
                        icon = Icons.Outlined.DeleteOutline,
                        iconContentDescription = stringResource(R.string.content_desc_delete_recipe),
                        onClick = { onDelete(); menuExpanded = false; }
                    )
                }
            }
        }
    )
}

@Composable
private fun Content(
    data: RecipeDetailsScreenUi
) {
    LazyColumn {
        item {
            UpperSection(data)
        }
        items(data.ingredients) { ingredient ->
            IngredientListItem(ingredient)
            Divider()
        }
        item {
            Text(
                text = stringResource(R.string.recipe_details_instructions_title),
                Modifier.padding(AppTheme.spaces.xl),
                style = AppTheme.typography.h6
            )
        }
        items(data.instructions) { instruction ->
            Text(
                text = "${instruction.number}. ${instruction.text}",
                Modifier.padding(horizontal = AppTheme.spaces.xl),
                style = AppTheme.typography.body2
            )
            Spacer(Modifier.height(AppTheme.spaces.xl))
        }
        if (data.instructions.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.recipe_details_no_instructions),
                    Modifier.padding(horizontal = AppTheme.spaces.xl),
                    color = modifiedColor(alpha = ContentAlpha.disabled),
                    style = AppTheme.typography.body2,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        item {
            LowerSection(data)
        }
    }
}

@Composable
private fun UpperSection(
    data: RecipeDetailsScreenUi
) {
    GeneralImage(
        image = data.image,
        contentDescription = null,
        Modifier
            .fillMaxWidth()
            .requiredHeight(240.dp),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.placeholder_recipe)
    )

    Column(
        Modifier.padding(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        Column {
            Text(
                text = data.title,
                style = AppTheme.typography.h6
            )
            if (data.author != null) {
                Text(
                    text = " by ${data.author}",
                    style = AppTheme.typography.caption,
                    color = modifiedColor(alpha = ContentAlpha.medium)
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.recipe_details_servings_title),
                    style = AppTheme.typography.body2,
                    color = modifiedColor(alpha = ContentAlpha.medium)
                )
                Text(
                    text = data.servings?.toString() ?: "-",
                    style = AppTheme.typography.subtitle2
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.recipe_details_ready_in_title),
                    style = AppTheme.typography.body2,
                    color = modifiedColor(alpha = ContentAlpha.medium)
                )
                Text(
                    text = data.readyInMinutes?.let {
                        stringResource(R.string.recipe_details_ready_in_value, it)
                    } ?: "-",
                    style = AppTheme.typography.subtitle2
                )
            }
        }

    }
}

@Composable
private fun LowerSection(
    data: RecipeDetailsScreenUi
) {
    Column(
        Modifier.padding(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        Text(
            text = stringResource(R.string.recipe_details_summary_title),
            style = AppTheme.typography.h6
        )
        if (data.summary != null) {
            HtmlText(
                text = data.summary,
                style = AppTheme.typography.body2.copy(
                    color = AppTheme.colors.onSurface.copy(
                        alpha = ContentAlpha.high
                    ),
                ),
                linkClicked = { linkString ->
                    println(linkString)
                }
            )
        } else {
            Text(
                text = stringResource(R.string.recipe_details_no_summary),
                color = modifiedColor(alpha = ContentAlpha.disabled),
                style = AppTheme.typography.body2,
                fontStyle = FontStyle.Italic
            )
        }

        if (data.sourceUrl != null) {
            val sourceText = stringResource(
                R.string.recipe_details_source_prefix,
                data.creditsText ?: ""
            ) + "\n${data.sourceUrl.toHtmlAnchor()}"

            HtmlText(
                text = sourceText,
                style = AppTheme.typography.caption
            )
        }
        if (data.spoonacularSourceUrl != null) {
            val spoonacularSourceText =
                stringResource(R.string.recipe_details_visit_on_spoonacular) +
                    "\n${data.spoonacularSourceUrl.toHtmlAnchor()}"

            HtmlText(
                text = spoonacularSourceText,
                style = AppTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    AppTheme {
        Surface {
            Content(RecipeDetailsScreenUi.PREVIEW)
        }
    }
}