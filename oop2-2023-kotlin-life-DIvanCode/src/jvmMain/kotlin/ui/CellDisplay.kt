package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import server.Cell

@Composable
fun CellDisplay(cell: Cell,
                onColorChange: () -> Unit) {
    println("CellDisplay(${cell.row}, ${cell.col})")

    val mutableColor = mutableStateOf(ColorPicker.colorByState(cell.state))

    Box(modifier = Modifier.
        size(10.dp, 10.dp).
        border(BorderStroke(1.dp, Color.Black)).
        background(mutableColor.value).
        clickable(onClick = {
            mutableColor.value = ColorPicker.pickedColor()
            cell.state = ColorPicker.pickedState()
            onColorChange()
        }))
}