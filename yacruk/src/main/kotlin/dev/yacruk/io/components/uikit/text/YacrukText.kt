package dev.yacruk.io.components.uikit.text

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.core.ext.marquee
import dev.yacruk.io.core.ext.noRippleClickable
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.fontSize

// TODO: preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YacrukText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    textStyle: TextStyle,
    fontSize: TextUnit? = null,
    lineHeight: TextUnit? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    isMarqueeEnabled: Boolean = true,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "text" to text,
                "color" to color,
                "textStyle" to textStyle,
                "fontSize" to fontSize,
                "lineHeight" to lineHeight,
                "textAlign" to textAlign,
                "maxLines" to maxLines,
                "minLines" to minLines,
                "onTextLayout" to onTextLayout,
                "onClick" to onClick,
                "isMarqueeEnabled" to isMarqueeEnabled,
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

// TODO: preview
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

// TODO: text
@Preview(showBackground = true)
@Composable
fun PreviewYacrukText() {
    YacrukTheme {
        YacrukText(
            text = "Lorem",
            textStyle = YacrukTheme.typography.headline,
        )
    }
}
