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
            currentScreen.value = 1
        },
        onLoadPreviousGame = {
            currentScreen.value = 2
        },
        onLoadExistingGame = {
            currentScreen.value = 2
        }
    ))

    screens.add(GameSettingsScreen {
        currentScreen.value = 2
    })

    screens.add(GameScreen())

//    screens.add(TestScreen())
//    currentScreen.value = screens.size - 1

    screens[currentScreen.value]()
}