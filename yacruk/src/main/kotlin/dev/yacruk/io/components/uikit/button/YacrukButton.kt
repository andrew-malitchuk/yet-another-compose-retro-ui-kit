package dev.yacruk.io.components.uikit.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.PressInteraction.Release
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.yacruk.io.components.uikit.button.YacrukButtonClickState.Clicked.toggleClick
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.ext.noRippleClickable
import dev.yacruk.io.core.ext.onTouch
import dev.yacruk.io.core.ext.yacrukBorder
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun YacrukButton(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    primaryState: YacrukButtonClickState = YacrukButtonClickState.Enabled,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val haptic = LocalHapticFeedback.current

    var clickState: YacrukButtonClickState by remember {
        mutableStateOf(primaryState)
    }

    var hoverStateState: YacrukButtonHoverState by remember {
        mutableStateOf(YacrukButtonHoverState.Default)
    }

    val backgroundColor = renkon_beige
    val borderColor = black_mesa
    val borderColorAlt = rustling_leaves
    val hoverColor = stone_craft

    val offset by animateDpAsState(
        targetValue =
        when (clickState) {
            YacrukButtonClickState.Clicked -> (strokeWidth * 2) + strokeWidth
            YacrukButtonClickState.Enabled -> (strokeWidth * 2)
            else -> (strokeWidth * 2)
        },
        label = "offset",
    )

    val borderColorAltState by animateColorAsState(
        targetValue =
        when (clickState) {
            YacrukButtonClickState.Clicked -> borderColorAlt
            else -> Color.Transparent
        },
        label = "borderColorAltState",
    )

    val backgroundColorState by animateColorAsState(
        targetValue =
        when (hoverStateState) {
            YacrukButtonHoverState.Hovered -> hoverColor
            else -> backgroundColor
        },
        label = "backgroundColorAltState",
    )

    val textSizeState by animateFloatAsState(
        targetValue =
        when (clickState) {
            YacrukButtonClickState.Clicked -> (YacrukTheme.typography.headline.fontSize.value * 0.9f)
            else -> YacrukTheme.typography.headline.fontSize.value
        },
        label = "borderColorAltState",
    )

    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    hoverStateState = YacrukButtonHoverState.Hovered
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    interactions.add(interaction)
                }

                is Release -> {
                    hoverStateState = YacrukButtonHoverState.Default
                    clickState = clickState.toggleClick()
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    interactions.remove(interaction.press)
                }

                is PressInteraction.Cancel -> {
                    hoverStateState = YacrukButtonHoverState.Default
                    interactions.remove(interaction.press)
                }

                is DragInteraction.Start -> {
                    interactions.add(interaction)
                }

                is DragInteraction.Stop -> {
                    interactions.remove(interaction.start)
                }

                is DragInteraction.Cancel -> {
                    hoverStateState = YacrukButtonHoverState.Default
                    clickState = YacrukButtonClickState.Disabled
                    interactions.add(interaction.start)
                }
            }
        }
    }


    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = true,
                onClick = { onClick?.invoke() }
            )
            .yacrukBorder(
                strokeWidth = strokeWidth,
                borderColor = borderColor,
                backgroundColor = backgroundColorState,
                borderColorAlt = borderColorAltState,
            )
    ) {
        YacrukText(
            modifier =
            Modifier
                .padding(
                    horizontal = offset,
                    vertical = strokeWidth * 2,
                )
                .height(
                    YacrukTheme.typography.headline.lineHeight.value.dp,
                ),
            text = "foobar",
            textStyle = YacrukTheme.typography.headline,
            fontSize = TextUnit(value = textSizeState, TextUnitType.Sp),
        )
    }
}

sealed class YacrukButtonClickState {
    data object Enabled : YacrukButtonClickState()

    data object Clicked : YacrukButtonClickState()

    data object Disabled : YacrukButtonClickState()

    fun YacrukButtonClickState.toggleClick(): YacrukButtonClickState {
        return when {
            this is Enabled -> Clicked
            else -> Enabled
        }
    }
}

sealed class YacrukButtonHoverState {
    data object Hovered : YacrukButtonHoverState()

    data object Default : YacrukButtonHoverState()
}

@Preview(showBackground = true)
@Composable
fun PreviewYacrukButton() {
    YacrukTheme {
        Box(modifier = Modifier.padding(YacrukTheme.spacing.medium)) {
            YacrukButton(
                strokeWidth = 4.dp,
                primaryState = YacrukButtonClickState.Clicked,
            )
        }
    }
}