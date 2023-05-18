package common

import kotlinx.serialization.Serializable

@Serializable
data class GameSettings(
    var fieldHeight: Int = 20,
    var fieldWidth: Int = 20,
    var cellColors: Int = 5,
    var aliveLeftBorder: Int = 2,
    var aliveRightBorder: Int = 3,
    var birthAmount: Int = 3
) {
    companion object {
        const val MAX_FIELD_SIZE = 1024
        const val MAX_COLORS = 12
        const val MAX_ALIVE_BORDER = 9
        const val MAX_BIRTH_AMOUNT = 9
    }
}