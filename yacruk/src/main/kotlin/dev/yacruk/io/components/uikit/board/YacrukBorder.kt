package dev.yacruk.io.components.uikit.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.yacruk.io.core.ext.bar
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.renkon_beige

@Composable
fun YacrukBorder(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    text: String,
    padding: Dp,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {
    val borderColor = black_mesa
    val backgroundColor = renkon_beige

    val initialPadding = strokeWidth * 2 + textStyle.fontSize.value.dp / 2

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .bar(
                    textStyle = textStyle,
                    strokeWidth = strokeWidth,
                    borderColor = borderColor,
                    backgroundColor = backgroundColor,
                    borderColorAlt = Color.Transparent,
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
