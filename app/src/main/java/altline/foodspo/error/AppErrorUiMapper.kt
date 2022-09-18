package altline.foodspo.error

import altline.foodspo.R
import altline.foodspo.data.error.*
import altline.foodspo.ui.core.Dictionary
import altline.foodspo.ui.core.component.InfoPanelUi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import javax.inject.Inject

class AppErrorUiMapper @Inject constructor(
    private val dictionary: Dictionary
) {

    fun toInfoPanel(
        error: AppException,
        retryAction: (() -> Unit)? = null
    ) = when (error) {
        is NotConnectedException -> InfoPanelUi(
            title = dictionary.getString(R.string.error_not_connected_title),
            message = dictionary.getString(R.string.error_not_connected_message),
            image = { rememberVectorPainter(Icons.Default.PublicOff) },
            actionLabel = dictionary.getString(R.string.action_retry),
            action = retryAction
        )
        is ServiceUnavailableException -> InfoPanelUi(
            title = dictionary.getString(R.string.error_service_unavailable_title),
            message = dictionary.getString(R.string.error_service_unavailable_message),
            image = { rememberVectorPainter(Icons.Default.CloudOff) },
            actionLabel = dictionary.getString(R.string.action_retry),
            action = retryAction
        )
        is AccessDeniedException -> InfoPanelUi(
            title = dictionary.getString(R.string.error_access_denied_title),
            message = dictionary.getString(R.string.error_access_denied_message),
            image = { rememberVectorPainter(Icons.Default.NotInterested) },
            actionLabel = dictionary.getString(R.string.action_retry),
            action = retryAction
        )
        is NotFoundException -> InfoPanelUi(
            title = dictionary.getString(R.string.error_not_found_title),
            message = dictionary.getString(R.string.error_not_found_message),
            image = { rememberVectorPainter(Icons.Default.FileDownloadOff) },
            actionLabel = dictionary.getString(R.string.action_retry),
            action = retryAction
        )
        else -> InfoPanelUi(
            title = dictionary.getString(R.string.error_unknown_title),
            message = dictionary.getString(R.string.error_unknown_message),
            image = { rememberVectorPainter(Icons.Default.ErrorOutline) },
            actionLabel = dictionary.getString(R.string.action_retry),
            action = retryAction
        )
    }

    fun toSnackbarMessage(error: AppException) = when (error) {
        is NotConnectedException -> dictionary.getString(R.string.error_not_connected_title)
        is AccessDeniedException -> dictionary.getString(R.string.error_access_denied_title)
        is NotFoundException -> dictionary.getString(R.string.error_not_found_title)
        is ServiceUnavailableException -> dictionary.getString(R.string.error_service_unavailable_title)
        is UnknownException -> dictionary.getString(R.string.error_unknown_title)
    }

}