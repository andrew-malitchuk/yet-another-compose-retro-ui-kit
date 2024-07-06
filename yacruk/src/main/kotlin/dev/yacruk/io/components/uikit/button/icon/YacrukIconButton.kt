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
import androidx.compose.runtime.saveable.rememberSaveable
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
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.true_navy

/**
 * A composable function that displays a clickable icon button with customizable size, border,
 * icon resource, interaction states, and colors.
 *
 * @param modifier [Optional] Modifier to apply to the button. Defaults to an empty Modifier.
 * @param borderWidth The width of the border in dp.
 * @param iconSize The size of the button in dp.
 * @param icon The resource ID of the icon to be displayed inside the button.
 * @param primaryState The initial state of the button (Enabled, Clicked, Disabled). Defaults to Enabled.
 * @param onClick The callback function to be invoked when the button is clicked. Can be null.
 * @param interactionSource A source for handling user interactions with the button.
 * @param iconOffset The padding applied inside the border. Defaults to YacrukTheme.spacing.small.
 * @param isDisabled Whether the button is disabled and not clickable. Defaults to false.
 * @param colors The color scheme for the button's border and icon tint. Defaults to the colors defined in `YacrukIconButtonDefaults.colors()`.
 */
@Composable
fun YacrukIconButton(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    iconSize: Dp,
    icon: Int,
    primaryState: YacrukIconButtonClickState = YacrukIconButtonClickState.Enabled,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = rememberSaveable { MutableInteractionSource() },
    iconOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
    colors: YacrukIconButtonColors = YacrukIconButtonDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "borderWidth" to borderWidth,
                "iconSize" to iconSize,
                "primaryState" to primaryState,
                "onClick" to onClick,
                "interactionSource" to interactionSource,
                "icon" to icon,
                "iconOffset" to iconOffset,
                "isDisabled" to isDisabled,
                "colors" to colors,
            ),
    )

    val haptic = LocalHapticFeedback.current

    var clickState: YacrukIconButtonClickState by rememberSaveable {
        mutableStateOf(primaryState)
    }

    val borderColorState by animateColorAsState(
        targetValue =
            when (clickState) {
                YacrukIconButtonClickState.Clicked ->
                    if (!isDisabled) {
                        colors.borderColorClicked
                    } else {
                        colors.borderColorAlt
                    }

                YacrukIconButtonClickState.Disabled -> Color.Cyan
                YacrukIconButtonClickState.Enabled ->
                    if (!isDisabled) {
                        colors.borderColor
                    } else {
                        colors.borderColorAlt
                    }
            },
        label = "borderColorState",
    )

    val interactions = rememberSaveable { mutableStateListOf<Interaction>() }
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

                is PressInteraction.Cancel ->
                    interactions.remove(interaction.press)

                is DragInteraction.Start ->
                    interactions.add(interaction)

                is DragInteraction.Stop ->
                    interactions.remove(interaction.start)

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
                    borderWidth = borderWidth,
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

class YacrukIconButtonColors internal constructor(
    val borderColor: Color,
    val borderColorAlt: Color,
    val borderColorClicked: Color,
)

object YacrukIconButtonDefaults {
    @Composable
    fun colors(
        borderColor: Color = black_mesa,
        borderColorAlt: Color = rustling_leaves,
        borderColorClicked: Color = true_navy,
    ) = YacrukIconButtonColors(
        borderColor = borderColor,
        borderColorAlt = borderColorAlt,
        borderColorClicked = borderColorClicked,
    )
}

@YacrukPreview
@Composable
private fun PreviewYacrukIconButton() {
    YacrukTheme {
        YacrukIconButton(
            borderWidth = 4.dp,
            icon = R.drawable.icon_check_24,
            iconSize = 48.dp,
            iconOffset = 2.dp,
        )
    }
}
