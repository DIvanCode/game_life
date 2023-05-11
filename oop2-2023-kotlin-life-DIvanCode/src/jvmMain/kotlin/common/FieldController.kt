package common

class FieldController(val field: Field) {
    fun get(row: Int, col: Int): Cell {
        return field.cells[index(row, col)]
    }

    fun getState(row: Int, col: Int): CellState {
        return field.cells[index(row, col)].state
    }

    fun setState(row: Int, col: Int, value: CellState) {
        field.cells[index(row, col)].state = value
    }

    fun isAlive(row: Int, col: Int): Boolean {
        return field.cells[index(row, col)].state.isAlive
    }

    fun getColor(row: Int, col: Int): Int {
        return field.cells[index(row, col)].state.color
    }

    fun insideField(row: Int, col: Int): Boolean {
        return row >= 0 && row < field.width &&
                col >= 0 && col < field.height
    }

    private fun index(row: Int, col: Int): Int {
        return field.width * row + col
    }
}