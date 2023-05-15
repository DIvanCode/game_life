package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ManageButton(
    var modifier: Modifier = Modifier.padding(8.dp).fillMaxWidth(),
    var text: String,
    var errorMessage: MutableState<String> = mutableStateOf("")
) {
    @Composable
    operator fun invoke(
        modifier: Modifier = this.modifier,
        onClick: () -> Unit
    ) {
        Column(modifier = modifier,
               horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = errorMessage.value, color = Color.Red)

            Button(modifier = modifier,
                onClick = {
                    errorMessage.value = ""
                    onClick()
                }
            ) {
                Text(text = text)
            }
        }
    }
}