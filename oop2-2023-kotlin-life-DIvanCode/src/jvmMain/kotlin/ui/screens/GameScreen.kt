package ui.screens

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import ui.components.Screen

class GameScreen: Screen() {
    @Composable
    override fun LazyItemScope.draw() {
        println("GameScreen")
    }
}