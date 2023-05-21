package client.models

data class Field(
    val height: Int,
    val width: Int,
    var cells: MutableList<CellState> = mutableListOf()
) {
    init {
        if (cells.isEmpty()) {
            for (row in 0 until height) {
                for (col in 0 until width) {
                    cells.add(CellState())
                }
            }
        }
    }

    fun state(row: Int, col: Int): CellState {
        return cells[index(row, col)]
    }

    fun setState(row: Int, col: Int, value: CellState) {
        cells[index(row, col)] = value
    }

    fun isAlive(row: Int, col: Int): Boolean {
        return cells[index(row, col)].isAlive
    }

    fun color(row: Int, col: Int): Int {
        return cells[index(row, col)].color
    }

    fun aliveGenerations(row: Int, col: Int): Int {
        return cells[index(row, col)].aliveGenerations
    }

    fun index(row: Int, col: Int): Int {
        return width * row + col
    }

    fun getNeighboursStates(row: Int, col: Int): MutableList<CellState> {
        val neighboursStates : MutableList<CellState> = mutableListOf()

        for (deltaRow in -1..1) {
            for (deltaCol in -1..1) {
                if (kotlin.math.abs(deltaRow) + kotlin.math.abs(deltaCol) == 0) {
                    continue
                }

                val neighbourRow = row + deltaRow
                val neighbourCol = col + deltaCol

                if (!inside(neighbourRow, neighbourCol)) {
                    continue
                }

                neighboursStates.add(state(neighbourRow, neighbourCol))
            }
        }

        return neighboursStates
    }

    fun change(newField: Field, isStep: Boolean = false): MutableList<Cell> {
        val changedCells: MutableList<Cell> = mutableListOf()

        for (row in 0 until height) {
            for (col in 0 until width) {
                if (color(row, col) != newField.color(row, col) ||
                    (color(row, col) == newField.color(row, col) && isAlive(row, col))) {
                    val cellState = newField.state(row, col)
                    if (isStep && cellState.isAlive) {
                        cellState.aliveGenerations++
                    } else {
                        cellState.aliveGenerations = 0
                    }

                    setState(row, col, cellState)

                    changedCells.add(Cell(row, col, state(row, col)))
                }
            }
        }

        return changedCells
    }

    private fun inside(row: Int, col: Int): Boolean {
        return row in 0 until height && col in 0 until width
    }
}