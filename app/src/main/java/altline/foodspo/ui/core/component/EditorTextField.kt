package altline.foodspo.ui.core.component

import altline.foodspo.util.OnKeyboardStateChange
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun EditorTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onApply: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text, TextRange(text.length)))
    }
    val focusRequester = remember { FocusRequester.Default }
    var initialFocusDone by remember { mutableStateOf(false) }

    OnKeyboardStateChange { state ->
        if (!state) LocalFocusManager.current.clearFocus()
    }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = {
            if (textFieldValue.text != it.text) {
                onTextChange(it.text)
            }
            textFieldValue = it
        },
        modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (initialFocusDone && !it.isFocused)
                    onApply()
                initialFocusDone = true
            },
        placeholder = placeholder
    )
    SideEffect {
        focusRequester.requestFocus()
    }
}