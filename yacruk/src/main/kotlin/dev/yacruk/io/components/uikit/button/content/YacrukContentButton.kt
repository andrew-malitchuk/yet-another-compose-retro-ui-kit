package dev.yacruk.io.components.uikit.button.content

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.components.uikit.button.content.YacrukContentButtonClickState.Clicked.toggleClick
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.ext.yacrukIconBorder
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.true_navy
import io.github.serpro69.kfaker.Faker

/**
 * A composable function that displays a clickable button with customizable size, border,
 * content, interaction states, and colors.
 *
 * @param modifier [Optional] Modifier to apply to the button. Defaults to an empty Modifier.
 * @param borderWidth The width of the border in dp.
 * @param contentSize The size of the button in dp.
 * @param content The composable content to be displayed inside the button.
 * @param primaryState The initial state of the button (Enabled, Clicked, Disabled). Defaults to Enabled.
 * @param onClick The callback function to be invoked when the button is clicked. Can be null.
 * @param interactionSource A source for handling user interactions with the button.
 * @param contentOffset The padding applied inside the border. Defaults to YacrukTheme.spacing.small.
 * @param isDisabled Whether the button is disabled and not clickable. Defaults to false.
 * @param colors The color scheme for the button's border and background. Defaults to the colors defined in `YacrukContentButtonDefaults.colors()`.
 */
@Composable
fun YacrukContentButton(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    contentSize: Dp,
    content: @Composable () -> Unit,
    primaryState: YacrukContentButtonClickState = YacrukContentButtonClickState.Enabled,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
    colors: YacrukContentButtonColors = YacrukContentButtonDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "borderWidth" to borderWidth,
                "contentSize" to contentSize,
                "primaryState" to primaryState,
                "onClick" to onClick,
                "interactionSource" to interactionSource,
                "content" to content,
                "contentOffset" to contentOffset,
                "isDisabled" to isDisabled,
                "colors" to colors,
            ),
    )

    val haptic = LocalHapticFeedback.current

    var clickState: YacrukContentButtonClickState by remember {
        mutableStateOf(primaryState)
    }

    val borderColorState by animateColorAsState(
        targetValue =
            when (clickState) {
                YacrukContentButtonClickState.Clicked ->
                    if (!isDisabled) {
                        colors.borderColorClicked
                    } else {
                        colors.borderColorAlt
                    }

                YacrukContentButtonClickState.Disabled -> Color.Cyan
                YacrukContentButtonClickState.Enabled ->
                    if (!isDisabled) {
                        colors.borderColor
                    } else {
                        colors.borderColorAlt
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

                is PressInteraction.Cancel ->
                    interactions.remove(interaction.press)

                is DragInteraction.Start ->
                    interactions.add(interaction)

                is DragInteraction.Stop ->
                    interactions.remove(interaction.start)

                is DragInteraction.Cancel -> {
                    clickState = YacrukContentButtonClickState.Disabled
                    interactions.add(interaction.start)
                }
            }
        }
    }

    Box(
        modifier =
            modifier
                .size(contentSize)
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
                .padding(contentOffset),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

sealed class YacrukContentButtonClickState {
    data object Enabled : YacrukContentButtonClickState()

    data object Clicked : YacrukContentButtonClickState()

    data object Disabled : YacrukContentButtonClickState()

    fun YacrukContentButtonClickState.toggleClick(): YacrukContentButtonClickState {
        return when {
            this is Enabled -> Clicked
            else -> Enabled
        }
    }
}

class YacrukContentButtonColors internal constructor(
    val borderColor: Color,
    val borderColorAlt: Color,
    val borderColorClicked: Color,
)

object YacrukContentButtonDefaults {
    @Composable
    fun colors(
        borderColor: Color = black_mesa,
        borderColorAlt: Color = rustling_leaves,
        borderColorClicked: Color = true_navy,
    ) = YacrukContentButtonColors(
        borderColor = borderColor,
        borderColorAlt = borderColorAlt,
        borderColorClicked = borderColorClicked,
    )
}

@YacrukPreview
@Composable
fun PreviewYacrukContentButton() {
    val faker = Faker()
    dev.yacruk.io.core.theme.source.YacrukTheme {
        YacrukContentButton(
            borderWidth = 4.dp,
            contentSize = 48.dp,
            primaryState = YacrukContentButtonClickState.Enabled,
            contentOffset = 8.dp,
            content = {
                YacrukText(
                    text = faker.code.asin(),
                    textStyle = YacrukTheme.typography.headline,
                )
            },
        )
    }
}
