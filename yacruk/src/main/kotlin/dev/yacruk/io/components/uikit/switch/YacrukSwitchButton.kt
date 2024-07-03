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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.true_navy

@Composable
fun YacrukSwitchButton(
    modifier: Modifier = Modifier,
    width: Dp = 72.dp,
    height: Dp = 40.dp,
    gapBetweenThumbAndTrackEdge: Dp = YacrukTheme.spacing.small,
    borderWidth: Dp = 2.dp,
    iconInnerPadding: Dp = YacrukTheme.spacing.extraSmall,
    thumbSize: Dp = 8.dp,
    initValue: Boolean = false,
    onStateChange: ((Boolean) -> Unit)? = null,
    colors: YacrukSwitchButtonColors = YacrukSwitchButtonDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "width" to width,
                "height" to height,
                "colors" to colors,
                "gapBetweenThumbAndTrackEdge" to gapBetweenThumbAndTrackEdge,
                "borderWidth" to borderWidth,
                "iconInnerPadding" to iconInnerPadding,
                "thumbSize" to thumbSize,
                "initValue" to initValue,
                "onStateChange" to onStateChange,
            ),
    )

    val interactionSource =
        rememberSaveable {
            MutableInteractionSource()
        }
    var onSideChangeState by rememberSaveable {
        mutableStateOf(initValue)
    }

    val alignment by animateAlignmentAsState(if (onSideChangeState) 1f else -1f)
    Box(
        modifier =
            modifier
                .size(width = width, height = height)
                .border(
                    width = borderWidth,
                    color = if (onSideChangeState) colors.checkedTrackColor else colors.uncheckedTrackColor,
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
                            color = if (onSideChangeState) colors.checkedTrackColor else colors.uncheckedTrackColor,
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
    return rememberSaveable { derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) } }
}

class YacrukSwitchButtonColors internal constructor(
    val checkedTrackColor: Color,
    val uncheckedTrackColor: Color,
)

object YacrukSwitchButtonDefaults {
    @Composable
    fun colors(
        checkedTrackColor: Color = true_navy,
        uncheckedTrackColor: Color = black_mesa,
    ) = YacrukSwitchButtonColors(
        checkedTrackColor = checkedTrackColor,
        uncheckedTrackColor = uncheckedTrackColor,
    )
}

@YacrukPreview
@Composable
private fun PreviewYacrukSwitchButton() {
    YacrukTheme {
        YacrukSwitchButton(
            borderWidth = 4.dp,
            thumbSize = 24.dp,
        )
    }
}
