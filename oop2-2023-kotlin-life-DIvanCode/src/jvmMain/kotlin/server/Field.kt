package server

class Field(val height: Int, val width: Int) {
    var cells: MutableList<Cell> = mutableListOf()

    init {
        cells = mutableListOf()
        for (row in 0 until height) {
            for (col in 0 until width) {
                cells.add(Cell(row, col))
            }
        }
    }

    fun getCell(row: Int, col: Int): Cell {
        return cells[height * row + col]
    }

    fun setCell(row: Int, col: Int, value: Cell) {
        cells[height * row + col] = value
    }

    fun getCellState(row: Int, col: Int): Int {
        return cells[height * row + col].state
    }

    fun setCellState(row: Int, col: Int, value: Int) {
        cells[height * row + col].state = value
    }

    private fun insideField(row: Int, col: Int): Boolean {
        return row >= 0 && row < height &&
                col >= 0 && col < width
    }

    fun getNeighboursStates(row: Int, col: Int): MutableList<Int> {
        val neighboursStates : MutableList<Int> = mutableListOf()

        for (deltaRow in -1..1) {
            for (deltaCol in -1..1) {
                if (kotlin.math.abs(deltaRow) + kotlin.math.abs(deltaCol) == 0) {
                    continue
                }

                val neighbourRow = row + deltaRow
                val neighbourCol = col + deltaCol

                if (!insideField(neighbourRow, neighbourCol)) {
                    continue
                }

                neighboursStates.add(getCellState(neighbourRow, neighbourCol))
            }
        }

        return neighboursStates
    }
}