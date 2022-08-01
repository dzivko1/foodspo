package altline.foodspo.ui.screen.recipeDetails

import altline.foodspo.R
import altline.foodspo.data.ingredient.model.Measure
import altline.foodspo.data.recipe.model.Instruction
import altline.foodspo.ui.core.component.GeneralImage
import altline.foodspo.ui.core.component.InfoPanel
import altline.foodspo.ui.core.component.LoadingSpinner
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.recipe.component.IngredientListItem
import altline.foodspo.ui.recipe.component.IngredientListItemUi
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import altline.foodspo.util.toHtmlAnchor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ireward.htmlcompose.HtmlText

@Composable
fun RecipeDetailsScreen(viewModel: RecipeDetailsViewModel = hiltViewModel()) {
    with(viewModel.uiState) {
        if (loading) LoadingSpinner()
        
        if (error != null) InfoPanel(error) { viewModel.loadRecipeDetails() }
        else Content(this)
    }
}

@Composable
private fun Content(
    state: RecipeDetailsUiState
) {
    LazyColumn {
        item {
            UpperSection(state)
        }
        items(state.ingredients) { ingredient ->
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
        items(state.instructions) { instruction ->
            Text(
                text = "${instruction.number}. ${instruction.text}",
                Modifier.padding(horizontal = AppTheme.spaces.xl),
                style = AppTheme.typography.body2
            )
            Spacer(Modifier.height(AppTheme.spaces.xl))
        }
        item {
            LowerSection(state)
        }
    }
}

@Composable
private fun UpperSection(
    recipe: RecipeDetailsUiState
) {
    GeneralImage(
        image = recipe.image,
        contentDescription = null,
        Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.placeholder_recipe)
    )
    
    Column(
        Modifier.padding(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        Column {
            Text(
                text = recipe.title,
                style = AppTheme.typography.h6
            )
            if (recipe.author != null) {
                Text(
                    text = " by ${recipe.author}",
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
                    text = recipe.servings?.toString() ?: "-",
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
                    text = recipe.readyInMinutes?.let {
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
    recipe: RecipeDetailsUiState
) {
    Column(
        Modifier.padding(AppTheme.spaces.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        if (recipe.summary != null) {
            Text(
                text = stringResource(R.string.recipe_details_about_recipe_title),
                style = AppTheme.typography.h6
            )
            HtmlText(
                text = recipe.summary,
                style = AppTheme.typography.body2,
                linkClicked = { linkString ->
                    println(linkString)
                }
            )
        }
        if (recipe.sourceUrl != null) {
            val sourceText = stringResource(
                R.string.recipe_details_source_prefix,
                recipe.creditsText ?: ""
            ) + "\n${recipe.sourceUrl.toHtmlAnchor()}"
            
            HtmlText(
                text = sourceText,
                style = AppTheme.typography.caption
            )
        }
        if (recipe.spoonacularSourceUrl != null) {
            val spoonacularSourceText =
                stringResource(R.string.recipe_details_visit_on_spoonacular) +
                        "\n${recipe.spoonacularSourceUrl.toHtmlAnchor()}"
            
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
        Content(
            RecipeDetailsUiState(
                id = 0,
                image = PlaceholderImages.recipe,
                title = "Bacon-Apple-Pecan Stuffed French Toast",
                author = "Maplewood Road",
                servings = 2,
                readyInMinutes = 30,
                ingredients = listOf(
                    IngredientListItemUi(
                        id = 0,
                        name = "Bread",
                        measure = Measure(
                            amount = 2.0,
                            unitLong = "slices",
                            unitShort = "slice"
                        )
                    ),
                    IngredientListItemUi(
                        id = 1,
                        name = "Apple",
                        measure = Measure(
                            amount = 0.25,
                            unitLong = "pieces",
                            unitShort = "piece"
                        )
                    )
                ),
                instructions = listOf(
                    Instruction(1, "Slice bread into 2 pieces."),
                    Instruction(2, "Slice an apple into pieces of desired length."),
                    Instruction(3, "Put apple on bread.")
                ),
                summary = "A food with bread, apples, and other things.",
                creditsText = "Maplewood Road",
                sourceUrl = "https://maplewoodroad.com/bacon-apple-pecan-stuffed-french-toast/",
                spoonacularSourceUrl = "https://spoonacular.com/bacon-apple-pecan-stuffed-french-toast-1697823"
            )
        )
    }
}