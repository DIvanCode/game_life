package common

import kotlinx.serialization.Serializable

@Serializable
data class CellState(
    var color: Int = 0,
    var isAlive: Boolean = color != 0,
    var aliveGenerations: Int = 0
)