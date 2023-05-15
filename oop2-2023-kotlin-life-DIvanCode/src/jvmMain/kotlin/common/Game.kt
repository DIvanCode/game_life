package common

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val settings: GameSettings,
    val field: Field =
        Field(settings.fieldHeight, settings.fieldWidth)
)