package client.displays

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

class CellDisplay(
    var color: MutableState<Color> = mutableStateOf(Color.White),
    var tooltip: MutableState<String> = mutableStateOf("0")
) {
    fun changeColor(color: Color) {
        this.color.value = color
    }

    fun changeTooltip(tooltip: String) {
        this.tooltip.value = tooltip
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    operator fun invoke(onClick: () -> Unit) {
        TooltipArea(
            tooltip = {
                Surface(
                    modifier = Modifier.shadow(4.dp),
                    color = Color(255, 255, 210),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = tooltip.value,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            },
            delayMillis = 600,
            tooltipPlacement = TooltipPlacement.CursorPoint(
                alignment = Alignment.BottomEnd,
                offset = DpOffset.Zero
            )
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .border(BorderStroke(1.dp, Color.Black))
                    .background(color.value)
                    .clickable(onClick = onClick)
            )
        }
    }
}