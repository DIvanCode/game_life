package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ColorPicker(
    val colors: Int
) {
    private var pickedId: Int = 1

    private fun pickColor(colorId: Int) {
        pickedId = colorId
        println("PICK COLOR ${COLORS[pickedId].toString()}")
    }

    fun pickedId(): Int {
        return pickedId
    }

    fun pickedColor(): Color {
        return COLORS[pickedId]
    }

    fun colorById(id: Int): Color {
        return COLORS[id]
    }

    @Composable
    operator fun invoke() {
        Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Center) {
            for (colorId in 0 until colors) {
                Box(modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp)
                        .border(1.dp, Color.Black)
                        .background(colorById(colorId))
                        .clickable(onClick = {
                            pickColor(colorId)
                        })
                )
            }
        }
    }

    companion object {
        val COLORS = arrayOf(
            Color.White,
            Color.Black,
            Color.Red,
            Color.Green,
            Color.Blue,
            Color.Yellow,
            Color.Cyan,
            Color.DarkGray,
            Color.Gray,
            Color.LightGray,
            Color.Magenta,
            Color.Transparent)
    }
}