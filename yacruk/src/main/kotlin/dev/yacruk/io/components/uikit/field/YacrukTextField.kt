package dev.yacruk.io.components.uikit.field

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import dev.yacruk.io.components.uikit.button.ordinary.YacrukButtonHoverState
import dev.yacruk.io.core.ext.clearFocusOnKeyboardDismiss
import dev.yacruk.io.core.ext.foo
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.jambalaya
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft

@Composable
fun YaaumBasicTextField(
    modifier: Modifier = Modifier,
    text: String? = null,
    onTextChanged: ((String) -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onCleanTextClick: (() -> Unit)? = null,
    strokeWidth: Dp,
) {
    val focusRequester = remember { FocusRequester() }

    val backgroundColor = renkon_beige
    val borderColor = black_mesa
    val borderColorAlt = rustling_leaves
    val hoverColor = stone_craft
    val disableColor = jambalaya

    val backgroundColorState by animateColorAsState(
        targetValue =
            when (YacrukButtonHoverState.Hovered) {
                YacrukButtonHoverState.Hovered -> hoverColor
//            YacrukButtonHoverState.Disabled -> disableColor
                else -> backgroundColor
            },
        label = "backgroundColorAltState",
    )

    var textState by remember { mutableStateOf(text) }

    var isOnFocus by remember { mutableStateOf(true) }
    val borderColorAltState: Color by animateColorAsState(
        if (isOnFocus) {
            borderColorAlt
        } else {
            Color.Transparent
        },
        label = "",
    )

    BasicTextField(
        modifier =
            modifier
                .focusRequester(focusRequester)
                .onFocusChanged { state ->
                    isOnFocus = state.isFocused
                }
                .clearFocusOnKeyboardDismiss(isOnFocus),
        value = textState ?: "",
        onValueChange = {
            onTextChanged?.invoke(it)
            textState = it
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = YacrukTheme.typography.button,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox = { innerTextField ->
            @Suppress("MagicNumber")
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = YacrukTheme.colors.background,
                        )
                        .foo(
                            strokeWidth = strokeWidth,
                            borderColor = borderColor,
                            backgroundColor = backgroundColorState,
                            borderColorAlt = borderColorAltState,
                        )
                    /*.border(
                        width = YacrukTheme.dividers.extraSmall,
                        color = bgColor,
                        shape = RoundedCornerShape(YacrukTheme.corners.medium),
                    )*/
                        .padding(all = YacrukTheme.spacing.medium),
                // inner padding
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(width = YacrukTheme.spacing.small))
                Box(
                    modifier =
                        Modifier
                            .weight(1f),
                ) {
                    innerTextField()
                }
            }
        },
    )
}
