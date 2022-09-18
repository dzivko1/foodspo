package altline.foodspo.ui.core.dialog

import altline.foodspo.R
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog

@Composable
fun ConfirmationDialog(data: DialogUi.Confirmation) {
    Dialog(
        onDismissRequest = data.onDismiss
    ) {
        Surface {
            Column(
                Modifier.padding(AppTheme.spaces.xl),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.medium)
            ) {
                Text(
                    text = data.title,
                    style = AppTheme.typography.subtitle2
                )
                if (data.message != null) {
                    Text(
                        text = data.message,
                        color = modifiedColor(alpha = ContentAlpha.medium),
                        style = AppTheme.typography.body2
                    )
                }
                Spacer(Modifier.height(AppTheme.spaces.large))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        AppTheme.spaces.xl,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Button(onClick = {
                        data.onConfirm()
                        if (data.dismissOnConfirm) data.onDismiss()
                    }) {
                        Text(stringResource(R.string.action_yes))
                    }
                    Button(onClick = data.onDismiss) {
                        Text(stringResource(R.string.action_no))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewConfirmationDialog() {
    AppTheme {
        ConfirmationDialog(
            DialogUi.Confirmation(
                title = "Title",
                message = "Message",
                onConfirm = {},
                onDismiss = {}
            )
        )
    }
}