package dev.yacruk.io.components.uikit.button.icon

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.R
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.components.uikit.button.icon.YacrukIconButtonClickState.Clicked.toggleClick
import dev.yacruk.io.core.ext.yacrukIconBorder
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft
import dev.yacruk.io.core.theme.source.color.true_navy

@Composable
fun YacrukIconButton(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    iconSize: Dp,
    primaryState: YacrukIconButtonClickState = YacrukIconButtonClickState.Enabled,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: Int,
    iconOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
    borderColor: Color,
    borderColorAlt: Color,
    borderColorClicked: Color
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "strokeWidth" to strokeWidth,
                "iconSize" to iconSize,
                "primaryState" to primaryState,
                "onClick" to onClick,
                "interactionSource" to interactionSource,
                "icon" to icon,
                "iconOffset" to iconOffset,
                "isDisabled" to isDisabled,
            ),
    )

    val haptic = LocalHapticFeedback.current

    var clickState: YacrukIconButtonClickState by remember {
        mutableStateOf(primaryState)
    }

    val borderColorState by animateColorAsState(
        targetValue =
            when (clickState) {
                YacrukIconButtonClickState.Clicked ->
                    if (!isDisabled) {
                        borderColorClicked
                    } else {
                        borderColorAlt
                    }

                YacrukIconButtonClickState.Disabled -> Color.Cyan
                YacrukIconButtonClickState.Enabled ->
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
                    clickState = YacrukIconButtonClickState.Disabled
                    interactions.add(interaction.start)
                }
            }
        }
    }

    Box(
        modifier =
            modifier
                .size(iconSize)
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
                .yacrukIconBorder(
                    strokeWidth = strokeWidth,
                    borderColor = borderColorState,
                    backgroundColor = Color.Transparent,
                )
                .padding(iconOffset),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = borderColorState,
        )
    }
}

sealed class YacrukIconButtonClickState {
    data object Enabled : YacrukIconButtonClickState()

    data object Clicked : YacrukIconButtonClickState()

    data object Disabled : YacrukIconButtonClickState()

    fun YacrukIconButtonClickState.toggleClick(): YacrukIconButtonClickState {
        return when {
            this is Enabled -> Clicked
            else -> Enabled
        }
    }
}

@YacrukPreview
@Composable
private fun PreviewYacrukIconButton() {
    YacrukTheme {
        YacrukIconButton(
            strokeWidth = 4.dp,
            icon = R.drawable.icon_check_24,
            iconSize = 48.dp,
            iconOffset = 2.dp,
            borderColor = black_mesa,
            borderColorAlt = rustling_leaves,
            borderColorClicked = true_navy,
        )
    }
}
