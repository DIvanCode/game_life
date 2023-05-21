package client.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class ManageDialog(
    val show: MutableState<Boolean> = mutableStateOf(false)
) {
    @Composable
    operator fun invoke(
        content: @Composable () -> Unit
    ) {
        if (show.value) {
            content()
        }
    }
}