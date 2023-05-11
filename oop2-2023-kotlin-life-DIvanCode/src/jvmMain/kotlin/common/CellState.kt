package common

data class CellState(var color: Int = 0,
                     var isAlive: Boolean = color != 0)