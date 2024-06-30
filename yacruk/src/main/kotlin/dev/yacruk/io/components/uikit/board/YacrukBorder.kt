package dev.yacruk.io.components.uikit.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.core.ext.bar
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.renkon_beige
import io.github.serpro69.kfaker.Faker

@Composable
fun YacrukBorder(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    text: String,
    borderColor: Color,
    backgroundColor: Color,
    padding: Dp,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {
    Rebugger(
        trackMap =
        mapOf(
            "modifier" to modifier,
            "strokeWidth" to strokeWidth,
            "text" to text,
            "padding" to padding,
            "textStyle" to textStyle,
            "content" to content,
        ),
    )


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

@YacrukPreview
@Composable
fun PreviewYacrukBorder() {
    val faker = Faker()
    YacrukTheme {
        YacrukBorder(
            strokeWidth = 4.dp,
            text = faker.cowboyBebop.character(),
            padding = 8.dp,
            textStyle = YacrukTheme.typography.title,
            backgroundColor = black_mesa,
            borderColor = renkon_beige
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
