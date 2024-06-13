package dev.yacruk.io.core.theme.source

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.yacruk.io.R
import dev.yacruk.io.core.theme.common.YacrukTypography

val typography = YacrukTypography(
    display = TextStyle(
        fontSize = fontSize.display,
        lineHeight = lineHeight.display,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily(
            Font(
                R.font.pixelcode_black,
                weight = FontWeight.Black,
                style = FontStyle.Normal,
            ),
        ),
    ),
    headline = TextStyle(
        fontSize = fontSize.headline,
        lineHeight = lineHeight.headline,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily(
            Font(
                R.font.pixelcode_black,
                weight = FontWeight.Black,
                style = FontStyle.Normal,
            ),
        ),
    ),
    title = TextStyle(
        fontSize = fontSize.title,
        lineHeight = lineHeight.title,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(
            Font(
                R.font.pixelcode_black,
                weight = FontWeight.Black,
                style = FontStyle.Normal,
            ),
        ),
    ),
    subHeading = TextStyle(
        fontSize = fontSize.subHeading,
        lineHeight = lineHeight.subHeading,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily(
            Font(
                R.font.pixelcode_black,
                weight = FontWeight.Black,
                style = FontStyle.Normal,
            ),
        ),
    ),
    body = TextStyle(
        fontSize = fontSize.body,
        lineHeight = lineHeight.body,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily(
            Font(
                R.font.pixelcode_black,
                weight = FontWeight.Black,
                style = FontStyle.Normal,
            ),
        ),
    ),
    caption = TextStyle(
        fontSize = fontSize.caption,
        lineHeight = lineHeight.caption,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily(
            Font(
                R.font.pixelcode_black,
                weight = FontWeight.Black,
                style = FontStyle.Normal,
            ),
        ),
    ),
    button = TextStyle(
        fontSize = fontSize.button,
        lineHeight = lineHeight.button,
        fontFamily = FontFamily(
            Font(
                R.font.pixelcode_bold,
                weight = FontWeight.Black,
                style = FontStyle.Normal,
            ),
        ),
    ),
)