package common

import kotlinx.serialization.Serializable

@Serializable
data class Cell(
    val row: Int,
    val col: Int,
    val state: CellState
)