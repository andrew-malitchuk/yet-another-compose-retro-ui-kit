package dev.yacruk.io.components.uikit.chip

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.PressInteraction.Release
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.R
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.components.uikit.chip.YacrukChipClickState.Clicked.toggleClick
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.ext.yacrukBorder
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.jambalaya
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft
import io.github.serpro69.kfaker.Faker

@Composable
fun YacrukChip(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    primaryState: YacrukChipClickState = YacrukChipClickState.Enabled,
    text: String,
    textStyle: TextStyle,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    iconOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "strokeWidth" to strokeWidth,
                "primaryState" to primaryState,
                "text" to text,
                "textStyle" to textStyle,
                "onClick" to onClick,
                "interactionSource" to interactionSource,
                "iconOffset" to iconOffset,
                "isDisabled" to isDisabled,
                "leadingIcon" to leadingIcon,
            ),
    )

    val haptic = LocalHapticFeedback.current

    var clickState: YacrukChipClickState by remember {
        mutableStateOf(primaryState)
    }
    var hoverStateState: YacrukChipHoverState by remember {
        mutableStateOf(YacrukChipHoverState.Default)
    }

    when {
        isDisabled -> {
            clickState = YacrukChipClickState.Enabled
            hoverStateState = YacrukChipHoverState.Disabled
        }

        !isDisabled -> {
            hoverStateState = YacrukChipHoverState.Default
        }
    }

    val backgroundColor = renkon_beige
    val borderColor = black_mesa
    val borderColorAlt = rustling_leaves
    val hoverColor = stone_craft
    val disableColor = jambalaya

    val offset by animateDpAsState(
        targetValue =
            when (clickState) {
                YacrukChipClickState.Clicked -> (strokeWidth * 2) + strokeWidth
                YacrukChipClickState.Enabled -> (strokeWidth * 2)
                else -> (strokeWidth * 2)
            },
        label = "offset",
    )

    val borderColorAltState by animateColorAsState(
        targetValue =
            when (clickState) {
                YacrukChipClickState.Clicked -> borderColorAlt
                YacrukChipClickState.Disabled -> disableColor
                else -> Color.Transparent
            },
        label = "borderColorAltState",
    )

    val backgroundColorState by animateColorAsState(
        targetValue =
            when (hoverStateState) {
                YacrukChipHoverState.Hovered -> hoverColor
                YacrukChipHoverState.Disabled -> disableColor
                else -> backgroundColor
            },
        label = "backgroundColorAltState",
    )

    val sizeState by animateFloatAsState(
        targetValue =
            when (clickState) {
                YacrukChipClickState.Clicked -> 0.9f
                else -> 1f
            },
        label = "borderColorAltState",
    )

    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    hoverStateState = YacrukChipHoverState.Hovered
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    interactions.add(interaction)
                }

                is Release -> {
                    hoverStateState = YacrukChipHoverState.Default
                    clickState = clickState.toggleClick()
//                    if (!isDisabled) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                    }
                    interactions.remove(interaction.press)
                }

                is PressInteraction.Cancel -> {
                    hoverStateState = YacrukChipHoverState.Default
                    interactions.remove(interaction.press)
                }

                is DragInteraction.Start -> {
                    interactions.add(interaction)
                }

                is DragInteraction.Stop -> {
                    interactions.remove(interaction.start)
                }

                is DragInteraction.Cancel -> {
                    hoverStateState = YacrukChipHoverState.Default
                    clickState = YacrukChipClickState.Disabled
                    interactions.add(interaction.start)
                }
            }
        }
    }

    Box(
        modifier =
            modifier
                .wrapContentWidth()
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
                    .wrapContentWidth()
                    .padding(
                        start = offset,
                        bottom = strokeWidth * 2,
                        top = strokeWidth * 2,
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingIcon?.let {
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
                    textStyle = textStyle,
                    fontSize =
                        TextUnit(
                            value = YacrukTheme.typography.headline.fontSize.value * sizeState,
                            TextUnitType.Sp,
                        ),
                )
                Spacer(modifier = Modifier.width(strokeWidth * 2 * sizeState))
            }
        }
    }
}

sealed class YacrukChipClickState {
    data object Enabled : YacrukChipClickState()

    data object Clicked : YacrukChipClickState()

    data object Disabled : YacrukChipClickState()

    fun YacrukChipClickState.toggleClick(): YacrukChipClickState {
        return when {
            this is Enabled -> Clicked
            else -> Enabled
        }
    }
}

sealed class YacrukChipHoverState {
    data object Hovered : YacrukChipHoverState()

    data object Disabled : YacrukChipHoverState()

    data object Default : YacrukChipHoverState()
}

@YacrukPreview
@Composable
private fun PreviewYacrukChip() {
    val faker = Faker()
    YacrukTheme {
        YacrukChip(
            strokeWidth = 4.dp,
            iconOffset = 4.dp,
            text = faker.cowboyBebop.character(),
            textStyle = YacrukTheme.typography.headline,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.icon_check_24),
                    contentDescription = "",
                )
            },
        )
    }
}
