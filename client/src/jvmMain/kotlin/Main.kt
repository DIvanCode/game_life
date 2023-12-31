package client

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Game Life",

    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            App(onClose = ::exitApplication)
        }
    }
}
