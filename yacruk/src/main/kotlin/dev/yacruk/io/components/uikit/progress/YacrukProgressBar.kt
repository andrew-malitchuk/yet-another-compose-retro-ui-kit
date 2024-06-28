package dev.yacruk.io.components.uikit.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@Composable
fun YacrukProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = Color.Cyan,
    backgroundColor: Color = Color.Red,
    clipShape: Shape = RectangleShape,
    height: Dp,
) {
    Box(
        modifier =
            modifier
                .clip(clipShape)
                .height(height)
                .drawBehind {
                    val (width) = size
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    drawLine(
                        color = backgroundColor,
                        start = Offset(0f, center.y),
                        end = Offset(width, center.y),
                        strokeWidth = (height / 2).toPx(),
                        pathEffect = pathEffect,
                    )
                },
    ) {
        Box(
            modifier =
                Modifier
                    .background(progressColor)
                    .fillMaxHeight()
                    .fillMaxWidth(progress),
        )
    }
}
