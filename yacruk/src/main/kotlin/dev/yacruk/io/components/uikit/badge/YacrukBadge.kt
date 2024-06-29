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

@Composable
fun YacrukBadge(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    badgeColor: Color,
    textColor: Color,
    shape: Shape,
    padding: Dp = 2.dp,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "text" to text,
                "textStyle" to textStyle,
                "badgeColor" to badgeColor,
                "textColor" to textColor,
                "shape" to shape,
                "padding" to padding,
            ),
    )

    Box(
        modifier =
            modifier
                .clip(shape)
                .background(badgeColor)
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
            color = textColor,
        )
    }
}

@YacrukPreview
@Composable
fun PreviewYacrukBadge() {
    val faker = Faker()
    dev.yacruk.io.core.theme.source.YacrukTheme {
        YacrukBadge(
            text = faker.idNumber.invalid(),
            textStyle = YacrukTheme.typography.headline,
            badgeColor = bittersweet,
            textColor = renkon_beige,
            shape = RectangleShape,
            padding = 8.dp,
        )
    }
}
