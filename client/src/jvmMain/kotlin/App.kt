package client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import client.components.Screen
import client.screens.GameScreen
import client.screens.GameSettingsScreen
import client.screens.GreetingScreen

@Composable
fun App(
    onClose: () -> Unit
) {
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
        },
        onExit = {
            onClose()
        }
    ))

    screens.add(GameSettingsScreen {
        currentScreen.value = 2
    })

    screens.add(GameScreen(
        onClose = {
            currentScreen.value = 0
        }
    ))

    screens[currentScreen.value]()
}