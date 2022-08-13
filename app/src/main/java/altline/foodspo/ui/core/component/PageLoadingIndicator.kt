package altline.foodspo.ui.core.component

import altline.foodspo.ui.theme.AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex

@Composable
fun PageLoadingIndicator() {
    Box(
        Modifier
            .fillMaxSize()
            .zIndex(2f)
            .background(AppTheme.colors.onBackground.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun PreviewLoadingIndicator() {
    AppTheme {
        PageLoadingIndicator()
    }
}