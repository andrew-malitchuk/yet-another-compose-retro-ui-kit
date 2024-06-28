package dev.yacruk.io.components.uikit.label

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.theme.common.YacrukTheme

// TODO: preview
@Composable
fun YacrukLabel(
    modifier: Modifier = Modifier,
    title: String,
    padding: Dp = YacrukTheme.spacing.small,
    textStyle: TextStyle,
    color: Color = Color.Unspecified,
    content: @Composable () -> Unit,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "title" to title,
                "padding" to padding,
                "textStyle" to textStyle,
                "color" to color,
                "content" to content,
            ),
    )

    Column(
        modifier =
            modifier
                .wrapContentHeight(),
    ) {
        YacrukText(text = title, textStyle = textStyle, color = color)
        Spacer(modifier = Modifier.height(padding))
        content()
    }
}

@Composable
fun YacrukLabel(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    padding: Dp = YacrukTheme.spacing.small,
    textStyle: TextStyle,
    color: Color = Color.Unspecified,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
            modifier
                .wrapContentHeight(),
    ) {
        YacrukText(textResId = titleResId, textStyle = textStyle, color = color)
        Spacer(modifier = Modifier.height(padding))
        content()
    }
}
