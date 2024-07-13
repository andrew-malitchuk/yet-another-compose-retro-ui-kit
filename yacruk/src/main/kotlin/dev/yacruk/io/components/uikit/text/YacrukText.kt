package dev.yacruk.io.components.uikit.text

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.core.ext.marquee
import dev.yacruk.io.core.ext.noRippleClickable
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.fontSize
import io.github.serpro69.kfaker.Faker

/**
 * A composable function that displays formatted text with customization options.
 *
 * @param modifier [Optional] Modifier to apply to the text layout. Defaults to an empty Modifier.
 * @param text The text content to be displayed.
 * @param textStyle The text style defining font, size, and other visual properties.
 * @param color The text color. Defaults to [Color.Unspecified].
 * @param fontSize [Optional] The font size for the text. Defaults to the font size defined in the textStyle.
 * @param lineHeight [Optional] The line height for the text. Defaults to the line height defined in the textStyle.
 * @param textAlign [Optional] The horizontal alignment of the text. Defaults to null (left-aligned).
 * @param maxLines The maximum number of lines to display. Defaults to Int.MAX_VALUE (unlimited).
 * @param minLines The minimum number of lines to display. Defaults to 1.
 * @param isMarqueeEnabled Whether to enable marquee behavior (text scrolls if it overflows). Defaults to true.
 * @param onTextLayout [Optional] A callback lambda to receive information about the text layout after it's placed.
 * @param onClick [Optional] A callback lambda to be invoked when the text is clicked.
 */
@Composable
fun YacrukText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    color: Color = Color.Unspecified,
    fontSize: TextUnit? = null,
    lineHeight: TextUnit? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    isMarqueeEnabled: Boolean = true,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "text" to text,
                "textStyle" to textStyle,
                "color" to color,
                "fontSize" to fontSize,
                "lineHeight" to lineHeight,
                "textAlign" to textAlign,
                "maxLines" to maxLines,
                "minLines" to minLines,
                "isMarqueeEnabled" to isMarqueeEnabled,
                "onTextLayout" to onTextLayout,
                "onClick" to onClick,
            ),
    )

    Text(
        text = text,
        modifier =
            modifier
                .noRippleClickable(onClick)
                .marquee(isMarqueeEnabled),
        color = color,
        style = textStyle,
        textAlign = textAlign,
        fontSize = fontSize ?: TextUnit.Unspecified,
        lineHeight = lineHeight ?: TextUnit.Unspecified,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
    )
}

@Composable
fun YacrukText(
    modifier: Modifier = Modifier,
    @StringRes textResId: Int,
    color: Color = Color.Unspecified,
    textStyle: TextStyle,
    fontSize: TextUnit? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    YacrukText(
        modifier = modifier,
        text = context.getText(textResId).toString(),
        color = color,
        textStyle = textStyle,
        fontSize = fontSize,
        textAlign = textAlign,
        lineHeight = lineHeight,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        onClick = onClick,
    )
}

@YacrukPreview
@Composable
private fun PreviewYacrukText() {
    val faker = Faker()
    YacrukTheme {
        YacrukText(
            text = faker.cowboyBebop.quote(),
            textStyle = YacrukTheme.typography.headline,
        )
    }
}
