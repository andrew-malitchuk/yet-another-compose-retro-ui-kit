package dev.yacruk.io.components.uikit.text

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.yacruk.io.core.ext.noRippleClickable
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme

// TODO: preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YacrukText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    textStyle: TextStyle,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    Text(
        text = text,
        modifier =
            modifier
                .noRippleClickable(onClick)
                .basicMarquee(),
        color = color,
        style = textStyle,
        textAlign = textAlign,
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
        textAlign = textAlign,
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
