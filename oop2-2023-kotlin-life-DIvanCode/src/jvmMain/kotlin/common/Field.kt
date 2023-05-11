package common

data class Field(val height: Int, val width: Int, var cells: MutableList<Cell> = mutableListOf())