package client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class OneRowTextField(
    var modifier: Modifier = Modifier.padding(8.dp).fillMaxWidth(),
    var placeholder: String = "",
    var text: MutableState<String> = mutableStateOf(""),
    var errorMessage: MutableState<String> = mutableStateOf("")
) {
    @Composable
    operator fun invoke(modifier: Modifier = this.modifier,
                        onChange: () -> Unit) {
        Column(modifier = modifier,
               horizontalAlignment = Alignment.CenterHorizontally) {
            if (text.value.isNotEmpty()) {
                Text(color = Color.Black, text = placeholder)
            }

            Box {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text.value,
                    onValueChange = {
                        text.value = it
                        errorMessage.value = ""
                        onChange()
                    },
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(text = placeholder)
                    },
                    singleLine = true,
                    trailingIcon = {
                        if (text.value.isNotEmpty()) {
                            if (errorMessage.value.isNotEmpty()) {
                                Icon(Icons.Filled.Warning,
                                    errorMessage.value,
                                    tint = Color.Red
                                )
                            } else {
                                Icon(Icons.Filled.Check,
                                    "",
                                    tint = Color.Green
                                )
                            }
                        }
                    }
                )
            }

            if (errorMessage.value.isNotEmpty()) {
                Text(text = errorMessage.value, color = Color.Red)
            }
        }
    }
}