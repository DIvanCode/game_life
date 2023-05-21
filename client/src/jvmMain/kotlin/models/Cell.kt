package client.models

data class Cell(
    val row: Int,
    val col: Int,
    val state: CellState
)