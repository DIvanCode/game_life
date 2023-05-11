package ui.displays

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameSettingsTextDisplay(fieldHeight: MutableState<String> = mutableStateOf(""),
                            fieldWidth: MutableState<String> = mutableStateOf(""),
                            cellColors: MutableState<String> = mutableStateOf(""),
                            aliveLeftBorder: MutableState<String> = mutableStateOf(""),
                            aliveRightBorder: MutableState<String> = mutableStateOf(""),
                            birthAmount: MutableState<String> = mutableStateOf("")) {
    val text =
        "Высота поля: ${fieldHeight.value}\n" +
        "Ширина поля: ${fieldWidth.value}\n" +
        "Количество цветов: ${cellColors.value}\n" +
        "Живая клетка остаётся жить, если количество её соседей " +
        "с таким же цветом от ${aliveLeftBorder.value} до ${aliveRightBorder.value}\n" +
        "В клетке зарождается жизнь, если она имеет ровно ${birthAmount.value} " +
        "соседей одного цвета, и при этом такой цвет только один"
    Text(modifier = Modifier.padding(16.dp),
         text = text)
}