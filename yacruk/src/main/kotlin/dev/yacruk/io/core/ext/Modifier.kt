package dev.yacruk.io.core.ext

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp

fun Modifier.noRippleClickable(onClick: (() -> Unit)? = null): Modifier =
    composed {
        if (onClick != null) {
            this.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                onClick.invoke()
            }
        } else {
            this
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

fun Modifier.disableClickAndRipple(): Modifier =
    composed {
        this.clickable(
            enabled = false,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = { },
        )
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
            topLeft =
                Offset(
                    strokeWidth.toPx() / 2,
                    strokeWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - strokeWidth.toPx()),
                    height = (size.height - strokeWidth.toPx()),
                ),
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
                    strokeWidth.toPx() + strokeWidth.toPx() / 2,
                ),
            end =
                Offset(
                    x = strokeWidth.toPx(),
                    y = size.height - (strokeWidth / 2).toPx(),
                ),
            strokeWidth = strokeWidth.toPx(),
        )
    }

fun Modifier.foo(
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
            topLeft =
                Offset(
                    strokeWidth.toPx() / 2,
                    strokeWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - strokeWidth.toPx()),
                    height = (size.height - strokeWidth.toPx()),
                ),
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
                    strokeWidth.toPx() + strokeWidth.toPx() / 2,
                ),
            end =
                Offset(
                    x = strokeWidth.toPx(),
                    y = size.height - (strokeWidth / 2).toPx(),
                ),
            strokeWidth = strokeWidth.toPx(),
        )
    }

@Composable
fun Modifier.bar(
    textStyle: TextStyle,
    strokeWidth: Dp,
    backgroundColor: Color,
    borderColor: Color,
    borderColorAlt: Color,
    text: String,
): Modifier {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult =
        remember(text) {
            textMeasurer.measure(text, textStyle)
        }
    val textSize = textStyle.fontSize.value

    return this.drawBehind {
        drawRect(
            color = backgroundColor,
            topLeft =
                Offset(
                    strokeWidth.toPx() / 2,
                    strokeWidth.toPx() / 2 + textSize + strokeWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - strokeWidth.toPx()),
                    height = (size.height - strokeWidth.toPx() - textSize - strokeWidth.toPx() / 2),
                ),
        )

        drawRect(
            color = borderColor,
            style = Stroke(width = strokeWidth.toPx()),
            topLeft =
                Offset(
                    0f,
                    textSize / 2 + strokeWidth.toPx() * 2,
                ),
        )

        drawRect(
            color = backgroundColor,
            topLeft =
                Offset(
                    x = strokeWidth.toPx() * 4 - strokeWidth.toPx(),
                    y = strokeWidth.toPx(),
                ),
            size =
                Size(
                    width = textLayoutResult.size.width.toFloat() + strokeWidth.toPx() * 2,
                    height = textLayoutResult.size.height.toFloat(),
                ),
        )

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft =
                Offset(
                    x = strokeWidth.toPx() * 4,
                    y = 0f,
                ),
            color = borderColor,
        )

//        drawRect(
//            color = borderColor,
//            style = Stroke(width = strokeWidth.toPx()),
//        )
    }
}

fun Modifier.yacrukIconBorder(
    strokeWidth: Dp,
    backgroundColor: Color,
    borderColor: Color,
): Modifier =
    this.drawBehind {
        drawRect(
            color = borderColor,
            style = Stroke(width = strokeWidth.toPx()),
        )
        drawRect(
            color = backgroundColor,
            topLeft =
                Offset(
                    strokeWidth.toPx() / 2,
                    strokeWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - strokeWidth.toPx()),
                    height = (size.height - strokeWidth.toPx()),
                ),
        )
    }

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.marquee(isEnabled: Boolean): Modifier {
    return if (isEnabled) {
        this.basicMarquee()
    } else {
        this
    }
}

/**
 * Remove focus from keyboard
 */
@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(initial: Boolean = false): Modifier =
    composed {
        var isFocused by remember { mutableStateOf(initial) }
        var keyboardAppearedSinceLastFocused by remember { mutableStateOf(initial) }
        if (isFocused) {
            val imeIsVisible = WindowInsets.isImeVisible
            val focusManager = LocalFocusManager.current
            LaunchedEffect(imeIsVisible) {
                if (imeIsVisible) {
                    keyboardAppearedSinceLastFocused = true
                } else if (keyboardAppearedSinceLastFocused) {
                    focusManager.clearFocus()
                }
            }
        }
        onFocusEvent {
            if (isFocused != it.isFocused) {
                isFocused = it.isFocused
                if (isFocused) {
                    keyboardAppearedSinceLastFocused = false
                }
            }
        }
    }
