package server.controllers

import common.*

class GameController(val settings: GameSettings,
                     val fieldController: FieldController =
                         FieldController(Field(settings.fieldHeight, settings.fieldWidth)),
                     val game: Game = Game(settings, fieldController.field)) {
    fun makeStep(): Boolean {
        val newField = FieldController(Field(settings.fieldHeight, settings.fieldWidth))

        for (row in 0 until settings.fieldHeight) {
            for (col in 0 until settings.fieldWidth) {
                newField.setState(row, col, getNewState(row, col))
            }
        }

        var changed = false

        for (row in 0 until settings.fieldHeight) {
            for (col in 0 until settings.fieldWidth) {
                if (fieldController.getState(row, col).color != newField.getState(row, col).color) {
                    changed = true

                    val cellState = newField.getState(row, col)
                    fieldController.setState(row, col, cellState)
                }
            }
        }

        return changed
    }

    fun clear() {
        for (row in 0 until settings.fieldHeight) {
            for (col in 0 until settings.fieldWidth) {
                val cellState = CellState(color = 0)

                fieldController.setState(row, col, cellState)
            }
        }
    }

    fun fillRandom() {
        for (row in 0 until settings.fieldHeight) {
            for (col in 0 until settings.fieldWidth) {
                val cellColor = (0 until settings.cellColors).random()
                val cellState = CellState(cellColor)

                fieldController.setState(row, col, cellState)
            }
        }
    }

    private fun getNewState(row: Int, col: Int): CellState {
        val neighbourStates = getNeighboursStates(row, col)

        if (!fieldController.isAlive(row, col)) {
            var newState: CellState? = null
            for (color in 1 until settings.cellColors) {
                if (neighbourStates.count {it.color == color} == settings.birthAmount) {
                    if (newState == null) {
                        newState = CellState(color)
                    } else {
                        return CellState(0)
                    }
                }
            }

            return when(newState) {
                null -> CellState(0)
                else -> newState
            }
        }

        val sameNeighbours = neighbourStates.count { it.color == fieldController.getColor(row, col) }
        return if (sameNeighbours >= settings.aliveLeftBorder && sameNeighbours <= settings.aliveRightBorder) {
            fieldController.getState(row, col)
        } else {
            CellState(0)
        }
    }

    private fun getNeighboursStates(row: Int, col: Int): MutableList<CellState> {
        val neighboursStates : MutableList<CellState> = mutableListOf()

        for (deltaRow in -1..1) {
            for (deltaCol in -1..1) {
                if (kotlin.math.abs(deltaRow) + kotlin.math.abs(deltaCol) == 0) {
                    continue
                }

                val neighbourRow = row + deltaRow
                val neighbourCol = col + deltaCol

                if (!fieldController.insideField(neighbourRow, neighbourCol)) {
                    continue
                }

                neighboursStates.add(fieldController.getState(neighbourRow, neighbourCol))
            }
        }

        return neighboursStates
    }
}