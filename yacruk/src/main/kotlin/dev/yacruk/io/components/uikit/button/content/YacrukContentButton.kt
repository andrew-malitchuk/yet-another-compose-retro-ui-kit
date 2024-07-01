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

@Composable
fun YacrukContentButton(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    contentSize: Dp,
    primaryState: YacrukContentButtonClickState = YacrukContentButtonClickState.Enabled,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
    contentOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
    borderColor: Color,
    borderColorAlt: Color,
    borderColorClicked: Color,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "strokeWidth" to strokeWidth,
                "contentSize" to contentSize,
                "primaryState" to primaryState,
                "onClick" to onClick,
                "interactionSource" to interactionSource,
                "content" to content,
                "contentOffset" to contentOffset,
                "isDisabled" to isDisabled,
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
                        borderColorClicked
                    } else {
                        borderColorAlt
                    }

                YacrukContentButtonClickState.Disabled -> Color.Cyan
                YacrukContentButtonClickState.Enabled ->
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
                    strokeWidth = strokeWidth,
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

@YacrukPreview
@Composable
fun PreviewYacrukContentButton() {
    val faker = Faker()
    dev.yacruk.io.core.theme.source.YacrukTheme {
        YacrukContentButton(
            strokeWidth = 4.dp,
            contentSize = 48.dp,
            primaryState = YacrukContentButtonClickState.Enabled,
            contentOffset = 8.dp,
            borderColor = black_mesa,
            borderColorAlt = rustling_leaves,
            borderColorClicked = true_navy,
            content = {
                YacrukText(
                    text = faker.code.asin(),
                    textStyle = YacrukTheme.typography.headline,
                )
            },
        )
    }
}
