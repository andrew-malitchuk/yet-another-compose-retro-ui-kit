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
import dev.yacruk.io.core.ext.yacrukBorderAlt
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.jambalaya
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft
import io.github.serpro69.kfaker.Faker

/**
 * A composable that implements a clickable chip with customizable appearance and behavior.
 *
 * This composable offers visual feedback on hover and click interactions and supports
 * leading icons and custom click behavior.
 *
 * @param modifier [Optional] Modifier to apply to the chip. Defaults to an empty Modifier.
 * @param text The text displayed within the chip.
 * @param textStyle The text style to apply to the chip text.
 * @param borderWidth The width of the border around the chip in dp.
 * @param primaryState The initial state of the chip (Enabled, Clicked, or Disabled). Defaults to YacrukChipClickState.Enabled.
 * @param onClick A callback lambda invoked when the chip is clicked (if enabled).
 * @param interactionSource A mutable interaction source for handling user interactions. Defaults to a new `remember` MutableInteractionSource.
 * @param iconOffset The horizontal offset for the leading icon in dp. Defaults to YacrukTheme.spacing.small.
 * @param isDisabled Whether the chip is currently disabled for user interaction. Defaults to false.
 * @param leadingIcon A lambda composable that defines the content for the leading icon. Can be null.
 * @param colors The color scheme for the chip's various states (background, border, etc.). Defaults to the colors defined in `YacrukChipColorsDefaults.colors()`.
 */
@Composable
fun YacrukChip(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    borderWidth: Dp,
    primaryState: YacrukChipClickState = YacrukChipClickState.Enabled,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    iconOffset: Dp = YacrukTheme.spacing.small,
    isDisabled: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    colors: YacrukChipColors = YacrukChipColorsDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "text" to text,
                "textStyle" to textStyle,
                "borderWidth" to borderWidth,
                "primaryState" to primaryState,
                "onClick" to onClick,
                "interactionSource" to interactionSource,
                "iconOffset" to iconOffset,
                "isDisabled" to isDisabled,
                "leadingIcon" to leadingIcon,
                "colors" to colors,
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

        !isDisabled ->
            hoverStateState = YacrukChipHoverState.Default
    }

    val offset by animateDpAsState(
        targetValue =
            when (clickState) {
                YacrukChipClickState.Clicked -> (borderWidth * 2) + borderWidth
                YacrukChipClickState.Enabled -> (borderWidth * 2)
                else -> (borderWidth * 2)
            },
        label = "offset",
    )

    val borderColorAltState by animateColorAsState(
        targetValue =
            when (clickState) {
                YacrukChipClickState.Clicked -> colors.borderColorAlt
                YacrukChipClickState.Disabled -> colors.disableColor
                else -> Color.Transparent
            },
        label = "borderColorAltState",
    )

    val backgroundColorState by animateColorAsState(
        targetValue =
            when (hoverStateState) {
                YacrukChipHoverState.Hovered -> colors.hoverColor
                YacrukChipHoverState.Disabled -> colors.disableColor
                else -> colors.backgroundColor
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
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    interactions.remove(interaction.press)
                }

                is PressInteraction.Cancel -> {
                    hoverStateState = YacrukChipHoverState.Default
                    interactions.remove(interaction.press)
                }

                is DragInteraction.Start ->
                    interactions.add(interaction)

                is DragInteraction.Stop ->
                    interactions.remove(interaction.start)

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
                .yacrukBorderAlt(
                    borderWidth = borderWidth,
                    borderColor = colors.borderColor,
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
                        bottom = borderWidth * 2,
                        top = borderWidth * 2,
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
                Spacer(modifier = Modifier.width(borderWidth * 2 * sizeState))
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

class YacrukChipColors internal constructor(
    val backgroundColor: Color,
    val borderColor: Color,
    val borderColorAlt: Color,
    val hoverColor: Color,
    val disableColor: Color,
)

object YacrukChipColorsDefaults {
    @Composable
    fun colors(
        backgroundColor: Color = renkon_beige,
        borderColor: Color = black_mesa,
        borderColorAlt: Color = rustling_leaves,
        hoverColor: Color = stone_craft,
        disableColor: Color = jambalaya,
    ) = YacrukChipColors(
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        borderColorAlt = borderColorAlt,
        hoverColor = hoverColor,
        disableColor = disableColor,
    )
}

@YacrukPreview
@Composable
private fun PreviewYacrukChip() {
    val faker = Faker()
    YacrukTheme {
        YacrukChip(
            borderWidth = 4.dp,
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
