package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.interaction.Request
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import server.controllers.RequestController
import ui.system.FilePicker
import ui.components.ManageButton
import ui.components.Screen
import common.JsonHandler

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
        RequestController.handleRequest(Request(
            route = "/",
            method = Request.POST
        ))
        onCreateNewGame()
    }

    private fun loadPreviousGame() {
        RequestController.handleRequest(Request(
            route = "/previous",
            method = Request.POST
        ))
        onLoadPreviousGame()
    }

    private fun loadExistingGame(path: String) {
        val game = JsonHandler.readGame(path)
        RequestController.handleRequest(Request(
            route = "/game",
            method = Request.POST,
            body = Json.encodeToString(game)
        ))
        onLoadExistingGame()
    }
}