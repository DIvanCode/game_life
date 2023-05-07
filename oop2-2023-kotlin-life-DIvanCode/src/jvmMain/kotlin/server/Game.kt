package server

object Game {
    var field: Field = Field(0, 0)
    var gameRules: GameRules = GameRules()

    fun init(fieldHeight: Int, fieldWidth: Int) {
        field = Field(fieldHeight, fieldWidth)
        gameRules = GameRules()
    }

    fun setAliveLeftBorder(aliveLeftBorder: Int) {
        gameRules.aliveLeftBorder = aliveLeftBorder
    }

    fun setAliveRightBorder(aliveRightBorder: Int) {
        gameRules.aliveRightBorder = aliveRightBorder
    }

    fun setBirthAmount(birthAmount: Int) {
        gameRules.birthAmount = birthAmount
    }

    private fun getNewState(row: Int, col: Int): Int {
        val neighbourStates = field.getNeighboursStates(row, col)

        if (field.getCellState(row, col) == gameRules.emptyCellState) {
            var newState : Int? = null
            for (state in 0 until gameRules.cellStates) {
                if (neighbourStates.count {it == state} == gameRules.birthAmount) {
                    if (newState == null) {
                        newState = state
                    } else {
                        return gameRules.emptyCellState
                    }
                }
            }

            return when(newState) {
                null -> gameRules.emptyCellState
                else -> newState
            }
        }

        val sameNeighbours = neighbourStates.count { it == field.getCellState(row, col) }
        return if (sameNeighbours >= gameRules.aliveLeftBorder && sameNeighbours <= gameRules.aliveRightBorder) {
            field.getCellState(row, col)
        } else {
            gameRules.emptyCellState
        }
    }

    fun makeStep() {
        val newField = Field(field.height, field.width)

        for (row in 0 until field.height) {
            for (col in 0 until field.width) {
                newField.setCellState(row, col, getNewState(row, col))
            }
        }

        println("MAKE STEP")

        for (row in 0 until field.height) {
            for (col in 0 until field.width) {
                if (field.getCellState(row, col) != newField.getCellState(row, col)) {
                    field.setCell(row, col, newField.getCell(row, col))
                }
            }
        }
    }

    fun clear() {
        for (row in 0 until field.height) {
            for (col in 0 until field.width) {
                field.setCell(row, col, Cell(row, col, gameRules.emptyCellState))
            }
        }
    }
}