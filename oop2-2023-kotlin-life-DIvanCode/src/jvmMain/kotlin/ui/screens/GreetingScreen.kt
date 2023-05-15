package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.components.ManageButton
import ui.components.Screen

class GreetingScreen(
    val onCreateNewGame: () -> Unit,
    val onLoadPreviousGame: () -> Unit,
    val onLoadExistingGame: (Int) -> Unit
): Screen() {
    @Composable
    override fun LazyItemScope.draw() {
        val createNewGame = ManageButton(text = "Начать новую игру")
        val loadPreviousGame = ManageButton(text = "Загрузить предыдущую игру")
        val loadExistingGame = ManageButton(text = "Загрузить существующую игру")

        Column(modifier = Modifier.width(600.dp).height(500.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center) {
            createNewGame(modifier = Modifier.fillMaxWidth()) {
                onCreateNewGame()
            }
            loadPreviousGame(modifier = Modifier.fillMaxWidth()) {
                onLoadPreviousGame()
            }
            loadExistingGame(modifier = Modifier.fillMaxWidth()) {
                val gameId = 0
                onLoadExistingGame(gameId)
            }
        }
    }
}