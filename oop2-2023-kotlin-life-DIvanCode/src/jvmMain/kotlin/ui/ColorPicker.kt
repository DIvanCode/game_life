package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object ColorPicker {
    private val colors: Array<Color> = arrayOf(Color.White, Color.Black, Color.Red, Color.Green, Color.Blue)
    private var pickedState: Int = 1

    private fun pickColor(colorId: Int) {
        pickedState = colorId
        println("PICK COLOR ${colors[pickedState].toString()}")
    }

    fun pickedState(): Int {
        return pickedState
    }

    fun pickedColor(): Color {
        return colors[pickedState]
    }

    fun colorByState(state: Int): Color {
        return colors[state]
    }

    @Composable
    fun draw() {
        Row {
            for (colorId in colors.indices) {
                Box(modifier = Modifier.size(20.dp).padding(5.dp).border(1.dp, Color.Black).background(colors[colorId])
                    .clickable(onClick = { pickColor(colorId) })
                )
            }
        }
    }
}