package client.displays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import client.components.ColorPicker
import client.controllers.Controller
import client.models.Cell
import client.models.CellState
import client.models.GameSettings
import client.models.interaction.Response

class FieldDisplay(
    val gameSettings: GameSettings,
    val cellDisplays: MutableList<CellDisplay>,
    val colorPicker: ColorPicker
) {
    @Composable
    operator fun invoke(onColorChange: (Int, Int, Int) -> Unit) {
        LazyRow {
            item {
                Column {
                    for (row in 0 until gameSettings.fieldHeight) {
                        Row {
                            for (col in 0 until gameSettings.fieldWidth) {
                                val index = row * gameSettings.fieldWidth + col
                                val cellDisplay = cellDisplays[index]

                                cellDisplay {
                                    val response = Controller.change(Cell(row, col, CellState(colorPicker.pickedId())))

                                    if (response.status == Response.OK) {
                                        cellDisplay.changeColor(colorPicker.pickedColor())
                                        onColorChange(row, col, colorPicker.pickedId())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
