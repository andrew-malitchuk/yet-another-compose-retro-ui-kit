package dev.yacruk.io.core.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.noRippleClickable(onClick: (() -> Unit)? = null): Modifier =
    composed {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            onClick?.invoke()
        }
    }

fun Modifier.onTouch(
    onHover: (() -> Unit)? = null,
    onMove: (() -> Unit)? = null,
    onRelease: (() -> Unit)? = null,
): Modifier =
    composed {
        this.pointerInput(Unit) {
            awaitEachGesture {
                awaitFirstDown()
                // ACTION_DOWN here
                onHover?.invoke()
                do {
                    // This PointerEvent contains details including
                    // event, id, position and more
                    val event: PointerEvent = awaitPointerEvent()
                    // ACTION_MOVE loop
                    onMove?.invoke()
                    // Consuming event prevents other gestures or scroll to intercept
                    event.changes.forEach { pointerInputChange: PointerInputChange ->
                        pointerInputChange.consumePositionChange()
                    }
                } while (event.changes.any { it.pressed })

                // ACTION_UP is here
                onRelease?.invoke()
            }
        }
    }

fun Modifier.yacrukBorder(
    strokeWidth: Dp,
    backgroundColor: Color,
    borderColor: Color,
    borderColorAlt: Color,
): Modifier =
    this.drawBehind {
        drawRect(
            color = borderColor,
            style = Stroke(width = strokeWidth.toPx()),
        )
        drawRect(
            color = backgroundColor,
            topLeft = Offset(
                strokeWidth.toPx()/2,
                strokeWidth.toPx()/2,
            ),
            size = Size(
                width = (size.width-strokeWidth.toPx()),
                height = (size.height-strokeWidth.toPx())
            )
        )
        // horizontal
        drawLine(
            color = borderColorAlt,
            start =
                Offset(
                    (strokeWidth / 2).toPx(),
                    strokeWidth.toPx(),
                ),
            end =
                Offset(
                    x = size.width - (strokeWidth / 2).toPx(),
                    y = strokeWidth.toPx(),
                ),
            strokeWidth = strokeWidth.toPx(),
        )
        // vertical
        drawLine(
            color = borderColorAlt,
            start =
                Offset(
                    strokeWidth.toPx(),
                    strokeWidth.toPx()+strokeWidth.toPx()/2,
                ),
            end =
                Offset(
                    x = strokeWidth.toPx(),
                    y = size.height - (strokeWidth / 2).toPx(),
                ),
            strokeWidth = strokeWidth.toPx(),
        )
    }
