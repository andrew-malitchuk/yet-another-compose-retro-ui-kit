package dev.yacruk.io.components.uikit.switch

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.yacruk.io.core.theme.common.YacrukTheme

@Composable
fun YacrukSwitchButton(
    modifier: Modifier = Modifier,
    width: Dp = 72.dp,
    height: Dp = 40.dp,
    checkedTrackColor: Color = YacrukTheme.colors.primary,
    uncheckedTrackColor: Color = YacrukTheme.colors.secondary,
    gapBetweenThumbAndTrackEdge: Dp = YacrukTheme.spacing.small,
    borderWidth: Dp = 2.dp,
    iconInnerPadding: Dp = YacrukTheme.spacing.extraSmall,
    thumbSize: Dp = 8.dp,
    initValue: Boolean = false,
    onStateChange: ((Boolean) -> Unit)? = null,
    foo: Boolean? = null,
) {
    val interactionSource =
        remember {
            MutableInteractionSource()
        }
    var onSideChangeState by remember {
        mutableStateOf(initValue)
    }

    if (foo != null && foo != onSideChangeState) {
        onSideChangeState = foo
    }

    val alignment by animateAlignmentAsState(if (onSideChangeState) 1f else -1f)
    Box(
        modifier =
            modifier
                .size(width = width, height = height)
                .border(
                    width = borderWidth,
                    color = if (onSideChangeState) checkedTrackColor else uncheckedTrackColor,
                    shape = RectangleShape,
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                ) {
                    onSideChangeState = onSideChangeState.not()
                    onStateChange?.invoke(onSideChangeState)
                },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(
                        start = gapBetweenThumbAndTrackEdge,
                        end = gapBetweenThumbAndTrackEdge,
                    )
                    .fillMaxSize(),
            contentAlignment = alignment,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(size = thumbSize)
                        .background(
                            color = if (onSideChangeState) checkedTrackColor else uncheckedTrackColor,
                            shape = RectangleShape,
                        )
                        .padding(all = iconInnerPadding),
            )
        }
    }
}

@Composable
private fun animateAlignmentAsState(targetBiasValue: Float): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue, label = "")
    return remember { derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) } }
}
