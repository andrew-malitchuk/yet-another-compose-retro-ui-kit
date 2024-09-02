package dev.yacruk.io.core.theme.provider

import androidx.compose.runtime.staticCompositionLocalOf
import dev.yacruk.io.core.theme.common.YacrukColor
import dev.yacruk.io.core.theme.common.YacrukFontSize
import dev.yacruk.io.core.theme.common.YacrukLineHeight
import dev.yacruk.io.core.theme.common.YacrukSpacing
import dev.yacruk.io.core.theme.common.YacrukTypography

internal val LocalYacrukColors =
    staticCompositionLocalOf<YacrukColor> {
        error("No implementation")
    }

@Suppress("unused")
internal val LocalYacrukFontSize =
    staticCompositionLocalOf<YacrukFontSize> {
        error("No implementation")
    }

@Suppress("unused")
internal val LocalYacrukLineHeight =
    staticCompositionLocalOf<YacrukLineHeight> {
        error("No implementation")
    }

internal val LocalYacrukSpacing =
    staticCompositionLocalOf<YacrukSpacing> {
        error("No implementation")
    }

internal val LocalYacrukTypography =
    staticCompositionLocalOf<YacrukTypography> {
        error("No implementation")
    }
