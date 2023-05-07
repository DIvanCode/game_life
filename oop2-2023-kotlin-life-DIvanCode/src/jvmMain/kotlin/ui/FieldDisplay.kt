package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import server.Cell

@Composable
fun FieldDisplay(mutableCellsListState: MutableList<Cell>,
                 onColorChange: (Int, Int) -> Unit) {
    println("FieldDisplay")
    for (row in 0 until server.Game.field.height) {
        Row {
            for (col in 0 until server.Game.field.height) {
                val index = server.Game.field.height * row + col

                val mutableCell = mutableStateOf(mutableCellsListState[index])

                CellDisplay(mutableCell.value) {
                    mutableCell.value.state = ColorPicker.pickedState()
                    onColorChange(row, col)
                }
            }
        }
    }
}
