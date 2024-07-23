package dev.yacruk.io.components.uikit.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.bittersweet
import dev.yacruk.io.core.theme.source.color.renkon_beige
import io.github.serpro69.kfaker.Faker

/**
 * A composable function that displays a badge with customizable text, style, shape, and colors.
 *
 * @param modifier [Optional] Modifier to apply to the badge. Defaults to an empty Modifier.
 * @param text The text to display inside the badge.
 * @param textStyle The text style for the badge text.
 * @param shape The shape of the badge.
 * @param padding [Optional] Padding to apply inside the badge. Defaults to 2dp.
 * @param colors The color scheme for the badge. Defaults to the colors defined in `YacrukBadgeDefaults.colors()`.
 */
@Composable
fun YacrukBadge(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    shape: Shape,
    padding: Dp = 2.dp,
    colors: YacrukBadgeColors = YacrukBadgeDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "text" to text,
                "textStyle" to textStyle,
                "shape" to shape,
                "padding" to padding,
                "colors" to colors,
            ),
    )
    Box(
        modifier =
            modifier
                .clip(shape)
                .background(colors.badgeColor)
                .wrapContentSize()
                .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        YacrukText(
            modifier =
                Modifier
                    .height(
                        textStyle.lineHeight.value.dp,
                    ),
            text = text,
            textStyle = textStyle,
            color = colors.textColor,
        )
    }
}

class YacrukBadgeColors internal constructor(
    val badgeColor: Color,
    val textColor: Color,
)

object YacrukBadgeDefaults {
    @Composable
    fun colors(
        badgeColor: Color = bittersweet,
        textColor: Color = renkon_beige,
    ) = YacrukBadgeColors(
        badgeColor = badgeColor,
        textColor = textColor,
    )
}

@YacrukPreview
@Composable
fun PreviewYacrukBadge() {
    val faker = Faker()
    YacrukTheme {
        YacrukBadge(
            text = faker.idNumber.invalid(),
            textStyle = YacrukTheme.typography.headline,
            shape = RectangleShape,
            padding = 8.dp,
        )
    }
}
