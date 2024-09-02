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
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.true_navy

/**
 * A composable function that displays a determinate progress bar with a background and customizable colors.
 *
 * @param modifier [Optional] Modifier to apply to the progress bar. Defaults to an empty Modifier.
 * @param progress A value between 0.0 and 1.0 representing the current progress level.
 * @param height The height of the progress bar in dp.
 * @param clipShape The shape used to clip the progress bar (defaults to RectangleShape).
 * @param colors The color scheme for the progress bar's background and progress color. Defaults to the colors defined in `YacrukProgressBarColorsDefaults.colors()`.
 */
@Composable
fun YacrukProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    height: Dp,
    clipShape: Shape = RectangleShape,
    colors: YacrukProgressBarColors = YacrukProgressBarColorsDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "progress" to progress,
                "height" to height,
                "clipShape" to clipShape,
                "colors" to colors,
            ),
    )

    Box(
        modifier =
            modifier
                .clip(clipShape)
                .height(height)
                .drawBehind {
                    val (width) = size
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    drawLine(
                        color = colors.backgroundColor,
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
                    .background(colors.progressColor)
                    .fillMaxHeight()
                    .fillMaxWidth(progress),
        )
    }
}

class YacrukProgressBarColors internal constructor(
    val backgroundColor: Color,
    val progressColor: Color,
)

object YacrukProgressBarColorsDefaults {
    @Composable
    fun colors(
        backgroundColor: Color = black_mesa,
        progressColor: Color = true_navy,
    ) = YacrukProgressBarColors(
        backgroundColor = backgroundColor,
        progressColor = progressColor,
    )
}

@YacrukPreview
@Composable
private fun PreviewYacrukProgressBar() {
    YacrukTheme {
        YacrukProgressBar(
            modifier = Modifier.fillMaxWidth(),
            progress = 0.5f,
            height = 8.dp,
        )
    }
}
