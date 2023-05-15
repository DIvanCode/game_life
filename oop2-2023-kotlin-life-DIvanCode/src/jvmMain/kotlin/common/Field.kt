package common

import kotlinx.serialization.Serializable

@Serializable
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

    fun getState(row: Int, col: Int): CellState {
        return cells[index(row, col)]
    }

    fun setState(row: Int, col: Int, value: CellState) {
        cells[index(row, col)].color = value.color
        cells[index(row, col)].isAlive = value.isAlive
    }

    fun isAlive(row: Int, col: Int): Boolean {
        return cells[index(row, col)].isAlive
    }

    fun getColor(row: Int, col: Int): Int {
        return cells[index(row, col)].color
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

                neighboursStates.add(getState(neighbourRow, neighbourCol))
            }
        }

        return neighboursStates
    }

    fun change(newField: Field): MutableList<Cell> {
        val changedCells: MutableList<Cell> = mutableListOf()

        for (row in 0 until height) {
            for (col in 0 until width) {
                if (getState(row, col).color != newField.getState(row, col).color) {
                    val cellState = newField.getState(row, col)
                    setState(row, col, cellState)

                    changedCells.add(Cell(row, col, getState(row, col)))
                }
            }
        }

        return changedCells
    }

    private fun inside(row: Int, col: Int): Boolean {
        return row in 0 until width && col in 0 until height
    }
}