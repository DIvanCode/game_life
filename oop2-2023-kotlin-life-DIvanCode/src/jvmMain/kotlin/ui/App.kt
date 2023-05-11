package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import common.interaction.Request
import server.controllers.RequestController
import ui.components.Screen
import ui.screens.GameScreen
import ui.screens.GameSettingsScreen
import ui.screens.GreetingScreen

@Composable
fun App() {
    val screens: MutableList<Screen> = mutableListOf()
    val currentScreen = remember { mutableStateOf(0) }

    screens.add(GreetingScreen(
        onCreateNewGame = {
            RequestController.handleRequest(Request(
                route = "/",
                method = Request.POST
            ))

            currentScreen.value = 1
        },
        onLoadPreviousGame = {
            println("OK")
        },
        onLoadExistingGame = { gameId: Int ->
            println(gameId.toString())
        }
    ))

    screens.add(GameSettingsScreen {
        RequestController.handleRequest(Request(
            route = "/game",
            method = Request.POST
        ))

        currentScreen.value = 2
    })


    screens.add(GameScreen())

    screens[currentScreen.value]()
}