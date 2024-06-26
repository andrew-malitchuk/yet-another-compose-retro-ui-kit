package dev.yacruk.io.components.uikit.button.ordinary

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.R
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.components.uikit.button.ordinary.YacrukButtonClickState.Clicked.toggleClick
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
fun YacrukButton(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    primaryState: YacrukButtonClickState = YacrukButtonClickState.Enabled,
    text: String,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: (@Composable () -> Unit)? = null,
    iconOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "strokeWidth" to strokeWidth,
                "primaryState" to primaryState,
                "text" to text,
                "onClick" to onClick,
                "interactionSource" to interactionSource,
                "icon" to icon,
                "iconOffset" to iconOffset,
                "isDisabled" to isDisabled,
            ),
    )

    val haptic = LocalHapticFeedback.current

    var clickState: YacrukButtonClickState by remember {
        mutableStateOf(primaryState)
    }
    var hoverStateState: YacrukButtonHoverState by remember {
        mutableStateOf(YacrukButtonHoverState.Default)
    }

    when {
        isDisabled -> {
            clickState = YacrukButtonClickState.Enabled
            hoverStateState = YacrukButtonHoverState.Disabled
        }

        !isDisabled -> {
            hoverStateState = YacrukButtonHoverState.Default
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
                YacrukButtonClickState.Disabled -> disableColor
                else -> Color.Transparent
            },
        label = "borderColorAltState",
    )

    val backgroundColorState by animateColorAsState(
        targetValue =
            when (hoverStateState) {
                YacrukButtonHoverState.Hovered -> hoverColor
                YacrukButtonHoverState.Disabled -> disableColor
                else -> backgroundColor
            },
        label = "backgroundColorAltState",
    )

    val sizeState by animateFloatAsState(
        targetValue =
            when (clickState) {
                YacrukButtonClickState.Clicked -> 0.9f
                else -> 1f
            },
        label = "borderColorAltState",
    )

    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    hoverStateState = YacrukButtonHoverState.Hovered
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    interactions.add(interaction)
                }

                is Release -> {
                    hoverStateState = YacrukButtonHoverState.Default
                    clickState = clickState.toggleClick()
//                    if (!isDisabled) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                    }
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

    data object Disabled : YacrukButtonHoverState()

    data object Default : YacrukButtonHoverState()
}

@YacrukPreview
@Composable
private fun PreviewYacrukButton() {
    val faker = Faker()
    YacrukTheme {
        YacrukButton(
            strokeWidth = 4.dp,
            icon = {
                Icon(
                    painterResource(id = R.drawable.icon_check_24),
                    contentDescription = "",
                )
            },
            text = faker.cowboyBebop.quote(),
        )
    }
}
