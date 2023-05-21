package game_life.com.controllers

import game_life.com.models.*

class GameController(
    gameSettings: GameSettings,
    val game: Game =
        Game(gameSettings)
) {
    fun changeCell(cell: Cell) {
        game.field.setState(cell.row, cell.col, cell.state)
    }

    fun gen() {
        for (row in 0 until game.settings.fieldHeight) {
            for (col in 0 until game.settings.fieldWidth) {
                val cellState = game.field.state(row, col)
                cellState.aliveGenerations = 0
                game.field.setState(row, col, cellState)
            }
        }
    }

    fun clear(): MutableList<Cell> {
        gen()

        val newField = Field(game.settings.fieldHeight, game.settings.fieldWidth)

        for (row in 0 until game.settings.fieldHeight) {
            for (col in 0 until game.settings.fieldWidth) {
                val cellState = CellState(color = 0)

                newField.setState(row, col, cellState)
            }
        }

        return game.field.change(newField)
    }

    fun random(): MutableList<Cell> {
        gen()

        val newField = Field(game.settings.fieldHeight, game.settings.fieldWidth)

        for (row in 0 until game.settings.fieldHeight) {
            for (col in 0 until game.settings.fieldWidth) {
                val cellColor = (0 until game.settings.cellColors).random()
                val cellState = CellState(cellColor)

                newField.setState(row, col, cellState)
            }
        }

        return game.field.change(newField)
    }

    fun step(): MutableList<Cell> {
        val newField = Field(game.settings.fieldHeight, game.settings.fieldWidth)

        for (row in 0 until game.settings.fieldHeight) {
            for (col in 0 until game.settings.fieldWidth) {
                newField.setState(row, col, this.getNewCellState(row, col))
            }
        }

        return game.field.change(newField, isStep = true)
    }

    private fun getNewCellState(row: Int, col: Int): CellState {
        val neighbourStates = game.field.getNeighboursStates(row, col)

        if (!game.field.isAlive(row, col)) {
            var newState: CellState? = null
            for (color in 1 until game.settings.cellColors) {
                if (neighbourStates.count {it.color == color} == game.settings.birthAmount) {
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

        val sameNeighbours = neighbourStates.count { it.color == game.field.color(row, col) }
        return if (sameNeighbours >= game.settings.aliveLeftBorder && sameNeighbours <= game.settings.aliveRightBorder) {
            game.field.state(row, col)
        } else {
            CellState(0)
        }
    }
}