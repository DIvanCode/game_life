package client.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import client.system.FilePicker
import client.components.ManageButton
import client.components.Screen
import client.controllers.Controller
import client.models.JsonHandler

class GreetingScreen(
    val onCreateNewGame: () -> Unit,
    val onLoadPreviousGame: () -> Unit,
    val onLoadExistingGame: () -> Unit,
    val onExit: () -> Unit
): Screen() {
    @Composable
    override fun LazyItemScope.draw() {
        val createNewGameButton = ManageButton(text = "Начать новую игру")
        val loadPreviousGameButton = ManageButton(text = "Загрузить предыдущую игру")
        val loadExistingGameButton = ManageButton(text = "Загрузить существующую игру")
        val exitButton = ManageButton(text = "Выйти из игры")

        val filePicker = FilePicker()
        filePicker { path ->
            this@GreetingScreen.loadExistingGame(path)
        }

        Column(modifier = Modifier.width(600.dp).height(500.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center) {
            createNewGameButton(modifier = Modifier.fillMaxWidth()) {
                this@GreetingScreen.createNewGame()
            }
            loadPreviousGameButton(modifier = Modifier.fillMaxWidth()) {
                this@GreetingScreen.loadPreviousGame()
            }
            loadExistingGameButton(modifier = Modifier.fillMaxWidth()) {
                filePicker.open()
            }
            exitButton(modifier = Modifier.fillMaxWidth()) {
                onExit()
            }
        }
    }

    private fun createNewGame() {
        Controller.new()
        onCreateNewGame()
    }

    private fun loadPreviousGame() {
        Controller.load()
        onLoadPreviousGame()
    }

    private fun loadExistingGame(path: String) {
        val game = JsonHandler.load(path)
        Controller.postGame(game)
        onLoadExistingGame()
    }
}