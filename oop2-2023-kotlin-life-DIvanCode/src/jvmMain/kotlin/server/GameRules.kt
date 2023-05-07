package server

data class GameRules(var emptyCellState: Int = 0,
                     var cellStates: Int = 5,
                     var aliveLeftBorder: Int = 2,
                     var aliveRightBorder: Int = 3,
                     var birthAmount: Int = 3)