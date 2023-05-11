package ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.displays.MainHeaderDisplay

abstract class Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    operator fun invoke() {
        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.LightGray),
                   horizontalAlignment = Alignment.CenterHorizontally) {
            stickyHeader {
                MainHeaderDisplay()
            }

            item {
                draw()
            }
        }
    }

    @Composable
    abstract fun LazyItemScope.draw()
}