package dev.yacruk.io.components.uikit.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.yacruk.io.components.uikit.checkbox.YacrukCheckboxClickState.Clicked.toggleClick
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.ext.yacrukIconBorder
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft
import dev.yacruk.io.core.theme.source.color.true_navy

@Composable
fun YacrukCheckbox(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    primaryState: YacrukCheckboxClickState = YacrukCheckboxClickState.Enabled,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    isDisabled: Boolean = false,
    text: String? = null,
    textStyle: TextStyle,
    iconSize: Dp,
    textSpacing: Dp = 0.dp,
) {
    val haptic = LocalHapticFeedback.current

    var clickState: YacrukCheckboxClickState by remember {
        mutableStateOf(primaryState)
    }

    val backgroundColor = renkon_beige
    val borderColor = black_mesa
    val borderColorAlt = rustling_leaves
    val hoverColor = stone_craft
    val disableColor = rustling_leaves
    val borderColorClicked = true_navy

    val borderColorState by animateColorAsState(
        targetValue =
            when (clickState) {
                YacrukCheckboxClickState.Clicked ->
                    if (!isDisabled) {
                        borderColorClicked
                    } else {
                        borderColorAlt
                    }

                YacrukCheckboxClickState.Disabled -> Color.Cyan
                YacrukCheckboxClickState.Enabled ->
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
                    clickState = YacrukCheckboxClickState.Disabled
                    interactions.add(interaction.start)
                }
            }
        }
    }

    Row(
        modifier =
            Modifier.wrapContentSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = true,
                    onClick = {
                        if (!isDisabled) {
                            onClick?.invoke()
                        }
                    },
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                modifier
                    .size(iconSize)
                    .yacrukIconBorder(
                        strokeWidth = strokeWidth,
                        borderColor = borderColorState,
                        backgroundColor = Color.Transparent,
                    )
                    .padding((strokeWidth.value * 1.5).dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(borderColorState),
            )
        }
        text?.let {
            Spacer(modifier = Modifier.width(textSpacing))
            YacrukText(
                modifier =
                    Modifier
                        .height(
                            textStyle.lineHeight.value.dp,
                        ),
                text = it,
                textStyle = textStyle,
                color = borderColorState,
            )
        }
    }
}

sealed class YacrukCheckboxClickState {
    data object Enabled : YacrukCheckboxClickState()

    data object Clicked : YacrukCheckboxClickState()

    data object Disabled : YacrukCheckboxClickState()

    fun YacrukCheckboxClickState.toggleClick(): YacrukCheckboxClickState {
        return when {
            this is Enabled -> Clicked
            else -> Enabled
        }
    }
}
