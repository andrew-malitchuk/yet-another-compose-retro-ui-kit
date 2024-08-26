package dev.yacruk.io.components.uikit.badge.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.yacruk.io.core.theme.source.color.bittersweet
import dev.yacruk.io.core.theme.source.color.renkon_beige

object YacrukBadgeDefaults {
    @Composable
    fun colors(
        badgeColor: Color = bittersweet,
        textColor: Color = renkon_beige,
    ) = YacrukBadgeColors(
        badgeColor = badgeColor,
        textColor = textColor,
    )
}