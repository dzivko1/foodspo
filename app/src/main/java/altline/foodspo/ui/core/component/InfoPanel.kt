package altline.foodspo.ui.core.component

import altline.foodspo.R
import altline.foodspo.data.error.*
import altline.foodspo.ui.theme.AppTheme
import altline.foodspo.util.ProvideContentColor
import altline.foodspo.util.modifiedColor
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.IOException

@Composable
fun InfoPanel(error: Throwable, retryAction: (() -> Unit)? = null) = when (error) {
    is NotConnectedException -> InfoPanel(
        title = stringResource(R.string.error_not_connected_title),
        message = stringResource(R.string.error_not_connected_message),
        image = rememberVectorPainter(Icons.Default.PublicOff),
        actionLabel = stringResource(R.string.action_retry),
        action = retryAction
    )
    is ServiceUnavailableException -> InfoPanel(
        title = stringResource(R.string.error_service_unavailable_title),
        message = stringResource(R.string.error_service_unavailable_message),
        image = rememberVectorPainter(Icons.Default.CloudOff),
        actionLabel = stringResource(R.string.action_retry),
        action = retryAction
    )
    is AccessDeniedException -> InfoPanel(
        title = stringResource(R.string.error_access_denied_title),
        message = stringResource(R.string.error_access_denied_message),
        image = rememberVectorPainter(Icons.Default.NotInterested),
        actionLabel = stringResource(R.string.action_retry),
        action = retryAction
    )
    is NotFoundException -> InfoPanel(
        title = stringResource(R.string.error_not_found_title),
        message = stringResource(R.string.error_not_found_message),
        image = rememberVectorPainter(Icons.Default.FileDownloadOff),
        actionLabel = stringResource(R.string.action_retry),
        action = retryAction
    )
    else -> InfoPanel(
        title = stringResource(R.string.error_unknown_title),
        message = stringResource(R.string.error_unknown_message),
        image = rememberVectorPainter(Icons.Default.ErrorOutline),
        actionLabel = stringResource(R.string.action_retry),
        action = retryAction
    )
}

@Composable
fun InfoPanel(
    title: String? = null,
    message: String? = null,
    image: Painter? = null,
    actionLabel: String? = null,
    action: (() -> Unit)? = null
) {
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
                if (image != null) {
                    Icon(
                        image, contentDescription = null,
                        Modifier.size(128.dp),
                        tint = modifiedColor(alpha = 0.25f),
                    )
                }

                if (title != null) {
                    Text(
                        text = title,
                        style = AppTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }

                if (message != null) {
                    Text(
                        text = message,
                        style = AppTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }

                if (action != null) {
                    Spacer(Modifier.height(AppTheme.spaces.xxl))
                    Button(
                        onClick = action,
                        Modifier.defaultMinSize(minWidth = 200.dp)
                    ) {
                        if (actionLabel != null) Text(actionLabel.uppercase())
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewInfoPanel() {
    AppTheme {
        Surface {
            InfoPanel(
                "Title",
                "Message",
                rememberVectorPainter(Icons.Default.Error)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInfoPanel_NotConnected() {
    AppTheme {
        Surface {
            InfoPanel(NotConnectedException()) {}
        }
    }
}

@Preview
@Composable
private fun PreviewInfoPanel_ServiceUnavailable() {
    AppTheme {
        Surface {
            InfoPanel(ServiceUnavailableException()) {}
        }
    }
}

@Preview
@Composable
private fun PreviewInfoPanel_AccessDenied() {
    AppTheme {
        Surface {
            InfoPanel(AccessDeniedException()) {}
        }
    }
}

@Preview
@Composable
private fun PreviewInfoPanel_NotFound() {
    AppTheme {
        Surface {
            InfoPanel(NotFoundException()) {}
        }
    }
}

@Preview
@Composable
private fun PreviewInfoPanel_Unknown() {
    AppTheme {
        Surface {
            InfoPanel(UnknownException(IOException())) {}
        }
    }
}