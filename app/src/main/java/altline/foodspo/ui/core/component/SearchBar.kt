package altline.foodspo.ui.core.component

import altline.foodspo.R
import altline.foodspo.ui.theme.AppTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

data class SearchBarUi(
    val query: String,
    val onQueryChange: (String) -> Unit,
    val onSearch: (String) -> Unit,
    val searchDelayMillis: Long = 1000
) {
    companion object {
        @Composable
        fun preview(): SearchBarUi {
            var query by remember { mutableStateOf("") }
            return SearchBarUi(
                query = query,
                onQueryChange = { query = it },
                onSearch = {}
            )
        }
    }
}

@Composable
fun SearchBar(data: SearchBarUi) {
    var wasManualSearch by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester.Default }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = data.query,
            onValueChange = data.onQueryChange,
            Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            placeholder = { Text(stringResource(R.string.search_recipes_placeholder)) }
        )
        IconButton(
            onClick = {
                wasManualSearch = true
                data.onSearch(data.query)
            }
        ) {
            Icon(
                Icons.Outlined.Search,
                contentDescription = stringResource(R.string.content_desc_search_recipes)
            )
        }
    }

    LaunchedEffect(data.query) {
        wasManualSearch = false
        delay(data.searchDelayMillis)
        if (!wasManualSearch) data.onSearch(data.query)
    }

    SideEffect {
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
private fun PreviewSearchBar() {
    AppTheme {
        Surface {
            SearchBarUi.preview()
        }
    }
}