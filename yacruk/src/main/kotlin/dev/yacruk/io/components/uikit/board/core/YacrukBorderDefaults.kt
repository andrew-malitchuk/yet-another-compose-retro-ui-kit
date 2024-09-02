package dev.yacruk.io.components.uikit.board.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.renkon_beige

object YacrukBorderDefaults {
    @Composable
    fun colors(
        borderColor: Color = black_mesa,
        backgroundColor: Color = renkon_beige,
    ) = YacrukBorderColors(
        borderColor = borderColor,
        backgroundColor = backgroundColor,
    )
}