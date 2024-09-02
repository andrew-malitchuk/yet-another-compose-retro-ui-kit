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

/**
 * Creates a new modifier that applies a clickable behavior to the composable without the
 * default ripple effect.
 *
 * @param onClick An optional lambda function that defines the action to be performed when the
 *        user clicks on the composable. Can be null if click behavior is not desired.
 * @return A new `Modifier` instance with the clickable behavior applied (if `onClick` is not
 *         null) or the original modifier unchanged (if `onClick` is null).
 */
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

/**
 * Creates a new modifier that draws a custom border with an inner background and
 * accented lines on a composable.
 *
 * @param borderWidth The width of the border in dp.
 * @param backgroundColor The color used to fill the inner area of the border.
 * @param borderColor The color used for the outer edge of the border.
 * @param borderColorAlt The color used for the accented lines within the border.
 * @return A new `Modifier` with the custom border drawn behind the composable content.
 */
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

/**
 * Creates a new modifier that draws a custom border with an inner background on a composable.
 *
 * This border might be a simpler version of `yacrukBorderAlt` without the accented lines.
 *
 * @param borderWidth The width of the border in dp.
 * @param backgroundColor The color used to fill the inner area of the border.
 * @param borderColor The color used for the outer edge of the border.
 * @return A new `Modifier` with the custom border drawn behind the composable content.
 */
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

/**
 * Creates a new modifier that draws a custom border with an inner background and positions
 * provided text within the border.
 *
 * This function likely represents a specialized border specifically designed for displaying text.
 *
 * @param textStyle The text style to apply to the displayed text.
 * @param borderWidth The width of the border in dp.
 * @param backgroundColor The color used to fill the inner area of the border.
 * @param borderColor The color used for the outer edge of the border.
 * @param text The string content to be displayed within the border.
 * @return A new `Modifier` with the custom border and positioned text drawn behind the composable content.
 */
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

/**
 * Creates a new modifier that draws a custom border with an inner background around a composable,
 * likely intended for use with icons.
 *
 * @param borderWidth The width of the border in dp.
 * @param backgroundColor The color used to fill the inner area of the border.
 * @param borderColor The color used for the outer edge of the border.
 * @return A new `Modifier` with the custom border drawn behind the composable content.
 */
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

/**
 * Creates a new modifier that conditionally applies a marquee effect to the composable.
 *
 * The marquee effect animates the content horizontally to simulate scrolling text
 * that doesn't fit within the available width.
 *
 * @param isEnabled A flag indicating whether to enable the marquee effect.
 * @return A new `Modifier` with the marquee effect applied if `isEnabled` is true,
 *         otherwise the original modifier is returned.
 *
 * @OptIn(ExperimentalFoundationApi::class) This function uses an experimental Foundation API.
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.marquee(isEnabled: Boolean): Modifier {
    return if (isEnabled) {
        this.basicMarquee()
    } else {
        this
    }
}

/**
 * Creates a new modifier that clears focus from the composable when the software keyboard
 * is dismissed.
 *
 * This function is useful for composables that should lose focus when the user hides
 * the keyboard, preventing accidental interaction after typing.
 *
 * @param initial [Optional] Whether the composable should be initially focused (defaults to false).
 * @return A new `Modifier` that manages focus based on keyboard visibility.
 *
 * @OptIn(ExperimentalLayoutApi::class) This function uses an experimental Layout API.
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
