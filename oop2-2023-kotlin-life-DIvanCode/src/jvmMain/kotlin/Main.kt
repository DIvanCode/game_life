
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.ColorPicker
import ui.Game

@Composable
fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.background(Color.Yellow).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Text("Game Life", style = TextStyle(fontSize = 30.sp))
            }

            HelloContent()

            ColorPicker.draw()

            Game.draw()
        }
    }
}

fun main() = application {
    Game.init(30, 30)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Game Life"
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            App()
        }
    }
}
