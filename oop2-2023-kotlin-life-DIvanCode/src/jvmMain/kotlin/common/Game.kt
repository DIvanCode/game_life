package common

data class Game(val settings: GameSettings,
                val field: Field = Field(settings.fieldHeight, settings.fieldWidth))