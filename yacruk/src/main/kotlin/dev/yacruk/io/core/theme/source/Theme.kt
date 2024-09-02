package dev.yacruk.io.core.theme.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.yacruk.io.core.theme.provider.LocalYacrukColors
import dev.yacruk.io.core.theme.provider.LocalYacrukFontSize
import dev.yacruk.io.core.theme.provider.LocalYacrukSpacing
import dev.yacruk.io.core.theme.provider.LocalYacrukTypography
import dev.yacruk.io.core.theme.source.color.baseLightColorPalette

@Composable
@Suppress("FunctionNaming", "unused", "OptionalWhenBraces")
fun YacrukTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalYacrukColors provides baseLightColorPalette,
        LocalYacrukSpacing provides spacing,
        LocalYacrukTypography provides typography,
        LocalYacrukFontSize provides fontSize,
        content = content,
    )
}
