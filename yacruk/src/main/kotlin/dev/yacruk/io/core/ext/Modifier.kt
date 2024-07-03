package dev.yacruk.io.core.ext

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
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

fun Modifier.yacrukBorderAlt(
    borderWidth: Dp,
    backgroundColor: Color,
    borderColor: Color,
    borderColorAlt: Color,
): Modifier =
    this.drawBehind {
        drawRect(
            color = borderColor,
            style = Stroke(width = borderWidth.toPx()),
        )
        drawRect(
            color = backgroundColor,
            topLeft =
                Offset(
                    borderWidth.toPx() / 2,
                    borderWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - borderWidth.toPx()),
                    height = (size.height - borderWidth.toPx()),
                ),
        )
        // horizontal
        drawLine(
            color = borderColorAlt,
            start =
                Offset(
                    (borderWidth / 2).toPx(),
                    borderWidth.toPx(),
                ),
            end =
                Offset(
                    x = size.width - (borderWidth / 2).toPx(),
                    y = borderWidth.toPx(),
                ),
            strokeWidth = borderWidth.toPx(),
        )
        // vertical
        drawLine(
            color = borderColorAlt,
            start =
                Offset(
                    borderWidth.toPx(),
                    borderWidth.toPx() + borderWidth.toPx() / 2,
                ),
            end =
                Offset(
                    x = borderWidth.toPx(),
                    y = size.height - (borderWidth / 2).toPx(),
                ),
            strokeWidth = borderWidth.toPx(),
        )
    }

fun Modifier.yacrukBorder(
    borderWidth: Dp,
    backgroundColor: Color,
    borderColor: Color,
    borderColorAlt: Color,
): Modifier =
    this.drawBehind {
        drawRect(
            color = borderColor,
            style = Stroke(width = borderWidth.toPx()),
        )
        drawRect(
            color = backgroundColor,
            topLeft =
                Offset(
                    borderWidth.toPx() / 2,
                    borderWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - borderWidth.toPx()),
                    height = (size.height - borderWidth.toPx()),
                ),
        )
        // horizontal
        drawLine(
            color = borderColorAlt,
            start =
                Offset(
                    (borderWidth / 2).toPx(),
                    borderWidth.toPx(),
                ),
            end =
                Offset(
                    x = size.width - (borderWidth / 2).toPx(),
                    y = borderWidth.toPx(),
                ),
            strokeWidth = borderWidth.toPx(),
        )
        // vertical
        drawLine(
            color = borderColorAlt,
            start =
                Offset(
                    borderWidth.toPx(),
                    borderWidth.toPx() + borderWidth.toPx() / 2,
                ),
            end =
                Offset(
                    x = borderWidth.toPx(),
                    y = size.height - (borderWidth / 2).toPx(),
                ),
            strokeWidth = borderWidth.toPx(),
        )
    }

@Composable
fun Modifier.yacrukBorder(
    textStyle: TextStyle,
    borderWidth: Dp,
    backgroundColor: Color,
    borderColor: Color,
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
                    borderWidth.toPx() / 2,
                    borderWidth.toPx() / 2 + textSize + borderWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - borderWidth.toPx()),
                    height = (size.height - borderWidth.toPx() - textSize - borderWidth.toPx() / 2),
                ),
        )

        drawRect(
            color = borderColor,
            style = Stroke(width = borderWidth.toPx()),
            topLeft =
                Offset(
                    0f,
                    textSize / 2 + borderWidth.toPx() * 2,
                ),
        )

        drawRect(
            color = backgroundColor,
            topLeft =
                Offset(
                    x = borderWidth.toPx() * 4 - borderWidth.toPx(),
                    y = borderWidth.toPx(),
                ),
            size =
                Size(
                    width = textLayoutResult.size.width.toFloat() + borderWidth.toPx() * 2,
                    height = textLayoutResult.size.height.toFloat(),
                ),
        )

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft =
                Offset(
                    x = borderWidth.toPx() * 4,
                    y = 0f,
                ),
            color = borderColor,
        )
    }
}

fun Modifier.yacrukIconBorder(
    borderWidth: Dp,
    backgroundColor: Color,
    borderColor: Color,
): Modifier =
    this.drawBehind {
        drawRect(
            color = borderColor,
            style = Stroke(width = borderWidth.toPx()),
        )
        drawRect(
            color = backgroundColor,
            topLeft =
                Offset(
                    borderWidth.toPx() / 2,
                    borderWidth.toPx() / 2,
                ),
            size =
                Size(
                    width = (size.width - borderWidth.toPx()),
                    height = (size.height - borderWidth.toPx()),
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
