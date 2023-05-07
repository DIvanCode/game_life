package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManageButton(buttonText: String, action: () -> Unit) {
    Button(onClick = action, modifier = Modifier.padding(8.dp)) {
        Text(buttonText)
    }
}