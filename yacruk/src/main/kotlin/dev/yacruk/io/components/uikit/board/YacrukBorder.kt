package dev.yacruk.io.components.uikit.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.components.uikit.board.core.YacrukBorderColors
import dev.yacruk.io.components.uikit.board.core.YacrukBorderDefaults
import dev.yacruk.io.core.ext.yacrukBorder
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import io.github.serpro69.kfaker.Faker

/**
 * A composable function that displays a bordered container with customizable text, style, colors,
 * and inner content.
 *
 * @param modifier [Optional] Modifier to apply to the container. Defaults to an empty Modifier.
 * @param borderWidth The width of the border in dp.
 * @param text The text to display above the content.
 * @param padding The padding applied inside the border.
 * @param textStyle The text style for the displayed text.
 * @param colors The color scheme for the border and background. Defaults to the colors defined in `YacrukBorderDefaults.colors()`.
 * @param content The composable content to be displayed inside the border.
 */
@Composable
fun YacrukBorder(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    text: String,
    padding: Dp,
    textStyle: TextStyle,
    colors: YacrukBorderColors = YacrukBorderDefaults.colors(),
    content: @Composable () -> Unit,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "borderWidth" to borderWidth,
                "text" to text,
                "padding" to padding,
                "textStyle" to textStyle,
                "content" to content,
                "colors" to colors,
            ),
    )

    val initialPadding = borderWidth * 2 + textStyle.fontSize.value.dp / 2

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .yacrukBorder(
                    textStyle = textStyle,
                    borderWidth = borderWidth,
                    backgroundColor = colors.backgroundColor,
                    borderColor = colors.borderColor,
                    text = text,
                )
                .padding(
                    top = initialPadding + padding,
                    bottom = padding,
                    start = padding,
                    end =
                    padding,
                ),
    ) {
        content()
    }
}

@YacrukPreview
@Composable
fun PreviewYacrukBorder() {
    val faker = Faker()
    YacrukTheme {
        YacrukBorder(
            borderWidth = 4.dp,
            text = faker.cowboyBebop.character(),
            padding = 8.dp,
            textStyle = YacrukTheme.typography.title,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
            )
        }
    }
}
