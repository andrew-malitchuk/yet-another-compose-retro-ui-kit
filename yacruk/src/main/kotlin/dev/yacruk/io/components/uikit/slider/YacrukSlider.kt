package dev.yacruk.io.components.uikit.slider

import android.content.res.Resources
import android.util.TypedValue
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.true_navy
import kotlin.math.abs

@ExperimentalComposeUiApi
@Composable
fun YacrukSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChanged: (Float) -> Unit,
    borderWidth: Dp,
    pointerSize: Dp,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    stepSize: Float = 0.01f,
    colors: YacrukBarColors = YacrukBarColorsDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "value" to value,
                "onValueChanged" to onValueChanged,
                "borderWidth" to borderWidth,
                "pointerSize" to pointerSize,
                "enabled" to enabled,
                "valueRange" to valueRange,
                "stepSize" to stepSize,
                "colors" to colors,
            ),
    )

    val haptic = LocalHapticFeedback.current

    var pressed by remember { mutableStateOf(false) }
    var canvasSize by remember { mutableStateOf(Size(0f, 0f)) }
    var downX by remember { mutableStateOf(0f) }

    Canvas(
        modifier =
            modifier
                .pointerInteropFilter {
                    val range = valueRange.endInclusive - valueRange.start
                    val threshold = canvasSize.width / (range / stepSize)
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            if (!enabled) {
                                return@pointerInteropFilter false
                            }

                            val point =
                                Offset(
                                    (value - valueRange.start) / (valueRange.endInclusive - valueRange.start) * canvasSize.width,
                                    canvasSize.height / 2f,
                                )
                            if (it.x in (point.x - toPx(pointerSize))..(point.x + toPx(pointerSize)) &&
                                it.y in (point.y - toPx(pointerSize))..(point.y + toPx(pointerSize))
                            ) {
                                pressed = true
                                downX = it.x
                                true
                            } else {
                                false
                            }
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val dx = it.x - downX
                            if (abs(dx) >= threshold) {
                                val newValue = if (dx > 0) value + stepSize else value - stepSize
                                if (newValue in valueRange) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onValueChanged(newValue)
                                    downX = it.x
                                }
                            }
                            true
                        }

                        MotionEvent.ACTION_UP -> {
                            downX = 0f
                            pressed = false
                            true
                        }

                        else -> false
                    }
                },
    ) {
        canvasSize = size
        val (width) = size
        val point =
            Offset(
                (value - valueRange.start) / (valueRange.endInclusive - valueRange.start) * width,
                center.y,
            )
        // after
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawLine(
            color = colors.dotColor,
            start = Offset(0f, center.y),
            end = Offset(width, center.y),
            strokeWidth = borderWidth.toPx(),
            pathEffect = pathEffect,
        )
        // before
        drawLine(
            color = colors.borderColor,
            start = Offset(0f, center.y),
            end = point,
            strokeWidth = borderWidth.toPx(),
        )
        // pin
        drawRect(
            color = colors.borderColor,
            topLeft =
                Offset(
                    (value - valueRange.start) / (valueRange.endInclusive - valueRange.start) * width,
                    (center.y - (pointerSize.toPx() / 2)),
                ),
            size =
                Size(
                    width = pointerSize.toPx(),
                    height = pointerSize.toPx(),
                ),
        )
        // pin border
        drawRect(
            color = colors.dotColor,
            topLeft =
                Offset(
                    (value - valueRange.start) / (valueRange.endInclusive - valueRange.start) * width,
                    (center.y - (pointerSize.toPx() / 2)),
                ),
            style = Stroke(width = borderWidth.toPx()),
            size =
                Size(
                    width = pointerSize.toPx(),
                    height = pointerSize.toPx(),
                ),
        )
    }
}

private fun toPx(dp: Dp) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.value,
        Resources.getSystem().displayMetrics,
    )

class YacrukBarColors internal constructor(
    val borderColor: Color,
    val dotColor: Color,
)

object YacrukBarColorsDefaults {
    @Composable
    fun colors(
        borderColor: Color = true_navy,
        dotColor: Color = black_mesa,
    ) = YacrukBarColors(
        borderColor = borderColor,
        dotColor = dotColor,
    )
}

@YacrukPreview
@Composable
@ExperimentalComposeUiApi
private fun PreviewYacrukSlider() {
    YacrukTheme {
        YacrukSlider(
            value = 1f,
            onValueChanged = {
            },
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            valueRange = 0f..30f,
            stepSize = 2f,
            borderWidth = 4.dp,
            pointerSize = 12.dp,
        )
    }
}
