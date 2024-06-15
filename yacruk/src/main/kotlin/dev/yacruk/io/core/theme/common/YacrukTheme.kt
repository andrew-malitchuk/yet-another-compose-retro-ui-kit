package dev.yacruk.io.core.theme.common

import androidx.compose.runtime.Composable
import dev.yacruk.io.core.theme.provider.LocalYacrukColors
import dev.yacruk.io.core.theme.provider.LocalYacrukFontSize
import dev.yacruk.io.core.theme.provider.LocalYacrukLineHeight
import dev.yacruk.io.core.theme.provider.LocalYacrukSpacing
import dev.yacruk.io.core.theme.provider.LocalYacrukTypography

@Suppress("unused")
object YacrukTheme {
    val colors: YacrukColor
        @Composable
        get() = LocalYacrukColors.current
    val fontSize: YacrukFontSize
        @Composable
        get() = LocalYacrukFontSize.current
    val lineHeight: YacrukLineHeight
        @Composable
        get() = LocalYacrukLineHeight.current
    val spacing: YacrukSpacing
        @Composable
        get() = LocalYacrukSpacing.current
    val typography: YacrukTypography
        @Composable
        get() = LocalYacrukTypography.current
}
