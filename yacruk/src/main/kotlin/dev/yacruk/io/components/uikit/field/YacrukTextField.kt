package dev.yacruk.io.components.uikit.field

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.yacruk.io.components.uikit.button.icon.YacrukIconButtonClickState
import dev.yacruk.io.components.uikit.field.YacrukTextFieldState.Clicked.toggleClick
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.ext.yacrukBorder
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.jambalaya
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft

@Composable
fun YacrukTextField(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    primaryState: YacrukTextFieldState = YacrukTextFieldState.Enabled,
    text: String,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: (@Composable () -> Unit)? = null,
    iconOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
) {
    val haptic = LocalHapticFeedback.current

    var clickState: YacrukTextFieldState by remember {
        mutableStateOf(primaryState)
    }


    val backgroundColor = renkon_beige
    val borderColor = black_mesa
    val borderColorAlt = rustling_leaves
    val hoverColor = stone_craft
    val disableColor = jambalaya

    val borderColorState by animateColorAsState(
        targetValue =
        when (clickState) {
            YacrukTextFieldState.Clicked ->
                if (!isDisabled) {
                    borderColorClicked
                } else {
                    borderColorAlt
                }

            YacrukTextFieldState.Disabled -> Color.Cyan
            YacrukTextFieldState.Enabled ->
                if (!isDisabled) {
                    borderColor
                } else {
                    borderColorAlt
                }
        },
        label = "borderColorState",
    )

    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    interactions.add(interaction)
                }

                is PressInteraction.Release -> {
                    clickState = clickState.toggleClick()
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    interactions.remove(interaction.press)
                }

                is PressInteraction.Cancel -> {
                    interactions.remove(interaction.press)
                }

                is DragInteraction.Start -> {
                    interactions.add(interaction)
                }

                is DragInteraction.Stop -> {
                    interactions.remove(interaction.start)
                }

                is DragInteraction.Cancel -> {
                    clickState = YacrukTextFieldState.Disabled
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
                onClick = {
                    if (!isDisabled) {
                        onClick?.invoke()
                    }
                },
            )
            .yacrukBorder(
                strokeWidth = strokeWidth,
                borderColor = borderColor,
                backgroundColor = backgroundColorState,
                borderColorAlt = borderColorAltState,
            ),
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    start = offset,
                    bottom = strokeWidth * 2,
                    top = strokeWidth * 2,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.let {
                Box(modifier = Modifier.scale(sizeState)) {
                    it()
                }
                Spacer(modifier = Modifier.width(iconOffset * sizeState))
            }
            Row(
                modifier =
                Modifier
                    .height(
                        YacrukTheme.typography.headline.lineHeight.value.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                YacrukText(
                    modifier =
                    Modifier
                        .height(
                            YacrukTheme.typography.headline.lineHeight.value.dp * sizeState,
                        ),
                    text = text,
                    textStyle = YacrukTheme.typography.headline,
                    fontSize =
                    TextUnit(
                        value = YacrukTheme.typography.headline.fontSize.value * sizeState,
                        TextUnitType.Sp,
                    ),
                )
            }
        }
    }
}

sealed class YacrukTextFieldState {
    data object Enabled : YacrukTextFieldState()

    data object Clicked : YacrukTextFieldState()

    data object Disabled : YacrukTextFieldState()

    fun YacrukTextFieldState.toggleClick(): YacrukTextFieldState {
        return when {
            this is Enabled -> Clicked
            else -> Enabled
        }
    }
}
