package altline.foodspo.ui.screen.recipeEditor

import altline.foodspo.R
import altline.foodspo.data.core.model.ImageSrc
import altline.foodspo.ui.core.LocalNavController
import altline.foodspo.ui.core.ScreenBase
import altline.foodspo.ui.core.component.GeneralImage
import altline.foodspo.ui.core.component.ListEditor
import altline.foodspo.ui.core.component.ListEditorUi
import altline.foodspo.ui.placeholder.PlaceholderImages
import altline.foodspo.ui.theme.AppTheme
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.InsertPhoto
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

data class RecipeEditorScreenUi(
    val recipeId: String?,
    val title: String,
    val image: ImageSrc,
    val servings: String,
    val readyInMinutes: String,
    val ingredientEditorUi: ListEditorUi,
    val instructions: String,
    val summary: String,
    val onTitleChange: (String) -> Unit,
    val onNewImage: (ImageSrc) -> Unit,
    val onServingsChange: (String) -> Unit,
    val onReadyInMinutesChange: (String) -> Unit,
    val onInstructionsChange: (String) -> Unit,
    val onSummaryChange: (String) -> Unit,
    val onSaveRecipe: () -> Unit
) {
    companion object {
        @Composable
        fun preview() = RecipeEditorScreenUi(
            recipeId = null,
            title = "A new recipe",
            image = PlaceholderImages.imageSlot,
            servings = "2",
            readyInMinutes = "",
            ingredientEditorUi = ListEditorUi.PREVIEW,
            instructions = "Stir this, heat that",
            summary = "",
            onTitleChange = {},
            onNewImage = {},
            onServingsChange = {},
            onReadyInMinutesChange = {},
            onInstructionsChange = {},
            onSummaryChange = {},
            onSaveRecipe = {}
        )
    }
}

@Composable
fun RecipeEditorScreen(viewModel: RecipeEditorViewModel = hiltViewModel()) {
    ScreenBase(
        viewModel,
        topBar = { TopBar(isNewRecipe = viewModel.uiState.data?.recipeId == null) }
    ) {
        Content(it)
    }
}

@Composable
private fun TopBar(
    isNewRecipe: Boolean
) {
    val navController = LocalNavController.current
    TopAppBar(
        title = {
            Text(
                stringResource(
                    if (isNewRecipe) R.string.destination_title_new_recipe
                    else R.string.destination_title_edit_recipe
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = navController::navigateUp) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(R.string.content_desc_cancel)
                )
            }
        }
    )
}

@Composable
private fun Content(
    data: RecipeEditorScreenUi
) {
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            data.onNewImage(ImageSrc(bitmap))
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            data.onNewImage(ImageSrc(uri.toString()))
        }
    }

    Column(
        Modifier
            .padding(AppTheme.spaces.xl)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
    ) {
        TextField(
            value = data.title,
            onValueChange = data.onTitleChange,
            Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.recipe_editor_title_label)) }
        )
        GeneralImage(
            data.image,
            contentDescription = null,
            Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
        ) {
            Text(
                text = stringResource(R.string.recipe_editor_photo_label),
                Modifier.weight(1f),
                style = AppTheme.typography.subtitle1
            )
            Button(onClick = { cameraLauncher.launch() }) {
                Icon(
                    Icons.Outlined.AddAPhoto,
                    contentDescription = stringResource(R.string.content_desc_take_photo)
                )
            }
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Icon(
                    Icons.Outlined.InsertPhoto,
                    contentDescription = stringResource(R.string.content_desc_choose_image)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.xl)
        ) {
            NumericInput(
                value = data.servings,
                onValueChange = data.onServingsChange,
                label = stringResource(R.string.recipe_editor_servings_label),
                icon = Icons.Outlined.LocalPizza,
                rowScope = this
            )
            NumericInput(
                value = data.readyInMinutes,
                onValueChange = data.onReadyInMinutesChange,
                label = stringResource(R.string.recipe_editor_prep_time_label),
                icon = Icons.Outlined.AccessTime,
                rowScope = this
            )
        }

        Text(
            text = stringResource(R.string.recipe_editor_ingredients_title),
            style = AppTheme.typography.subtitle2
        )
        ListEditor(data.ingredientEditorUi)

        OutlinedTextField(
            value = data.instructions,
            onValueChange = data.onInstructionsChange,
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp),
            label = { Text(stringResource(R.string.recipe_editor_instructions_label)) }
        )
        OutlinedTextField(
            value = data.summary,
            onValueChange = data.onSummaryChange,
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp),
            label = { Text(stringResource(R.string.recipe_editor_summary_label)) }
        )
        Button(
            onClick = data.onSaveRecipe,
            Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Text(
                stringResource(R.string.recipe_editor_save_button).uppercase(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun NumericInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    rowScope: RowScope
) {
    with(rowScope) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            Modifier.weight(0.5f),
            label = { Text(label) },
            trailingIcon = { Icon(icon, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = {
                if (it.text.isEmpty())
                    TransformedText(AnnotatedString("-"), OffsetMapping.Identity)
                else TransformedText(it, OffsetMapping.Identity)
            }
        )
    }
}

@Preview
@Composable
private fun PreviewContent() {
    AppTheme {
        Surface {
            Content(RecipeEditorScreenUi.preview())
        }
    }
}