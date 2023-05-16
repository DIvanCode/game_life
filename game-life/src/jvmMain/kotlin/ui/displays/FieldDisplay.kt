package ui.displays

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import common.Cell
import common.CellState
import common.GameSettings
import common.interaction.Request
import common.interaction.Response
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import server.controllers.RequestController
import ui.components.ColorPicker

class FieldDisplay(
    val gameSettings: GameSettings,
    val cellDisplays: MutableList<CellDisplay>,
    val colorPicker: ColorPicker
) {
    @Composable
    operator fun invoke(onColorChange: (Int, Int, Int) -> Unit) {
        for (row in 0 until gameSettings.fieldHeight) {
            Row {
                for (col in 0 until gameSettings.fieldWidth) {
                    val index = row * gameSettings.fieldWidth + col
                    val cellDisplay = cellDisplays[index]

                    cellDisplay {
                        val response = RequestController.handleRequest(
                            Request(
                                route = "/game/cell/change",
                                method = Request.POST,
                                body = Json.encodeToString(Cell(
                                    row,
                                    col,
                                    CellState(colorPicker.pickedId())
                                ))
                            )
                        )
                        if (response.status == Response.OK) {
                            cellDisplay.changeColor(colorPicker.pickedColor())
                        }

                        onColorChange(row, col, colorPicker.pickedId())
                    }
                }
            }
        }
    }
}
