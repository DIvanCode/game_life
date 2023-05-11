package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object ColorPicker {
    private val colors: Array<Color> = arrayOf(Color.White, Color.Black, Color.Red, Color.Green, Color.Blue)
    private var pickedId: Int = 1

    private fun pickColor(colorId: Int) {
        pickedId = colorId
        println("PICK COLOR ${colors[pickedId].toString()}")
    }

    fun pickedId(): Int {
        return pickedId
    }

    fun pickedColor(): Color {
        return colors[pickedId]
    }

    fun colorById(id: Int): Color {
        return colors[id]
    }

    @Composable
    fun draw() {
        Row(modifier = Modifier.fillMaxWidth().background(Color.White),
            horizontalArrangement = Arrangement.Center) {
            for (colorId in colors.indices) {
                Box(modifier = Modifier.size(20.dp).padding(5.dp).border(1.dp, Color.Black).background(colors[colorId])
                    .clickable(onClick = { pickColor(colorId) })
                )
            }
        }
    }
}