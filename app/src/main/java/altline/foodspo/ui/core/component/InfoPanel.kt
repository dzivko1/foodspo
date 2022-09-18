package altline.foodspo.ui.core.component

import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.ProvideContentColor
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InfoPanel(data: InfoPanelUi) {
    ProvideContentColor(alpha = ContentAlpha.medium) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.padding(horizontal = AppTheme.spaces.xxl),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (data.image != null) {
                    Icon(
                        data.image.invoke(), contentDescription = null,
                        Modifier.size(128.dp),
                        tint = modifiedColor(alpha = 0.25f),
                    )
                }

                if (data.title != null) {
                    Text(
                        text = data.title,
                        style = AppTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }

                if (data.message != null) {
                    Text(
                        text = data.message,
                        style = AppTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }

                if (data.action != null) {
                    Spacer(Modifier.height(AppTheme.spaces.xxl))
                    Button(
                        onClick = data.action,
                        Modifier.defaultMinSize(minWidth = 200.dp)
                    ) {
                        if (data.actionLabel != null) Text(data.actionLabel.uppercase())
                    }
                }
            }
        }
    }
}

data class InfoPanelUi(
    val title: String? = null,
    val message: String? = null,
    val image: (@Composable () -> Painter)? = null,
    val actionLabel: String? = null,
    val action: (() -> Unit)? = null
) {
    companion object {
        @Composable
        fun preview() = InfoPanelUi(
            title = "Title",
            message = "Message",
            image = { rememberVectorPainter(Icons.Default.Error) }
        )
    }
}

@Preview
@Composable
private fun PreviewInfoPanel() {
    AppTheme {
        Surface {
            InfoPanel(InfoPanelUi.preview())
        }
    }
}