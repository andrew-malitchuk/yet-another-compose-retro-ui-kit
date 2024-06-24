package dev.yacruk.io.components.uikit.field

import android.text.Selection
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import dev.yacruk.io.R
import dev.yacruk.io.components.uikit.button.ordinary.YacrukButtonHoverState
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.ext.clearFocusOnKeyboardDismiss
import dev.yacruk.io.core.ext.foo
import dev.yacruk.io.core.ext.marquee
import dev.yacruk.io.core.ext.noRippleClickable
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.jambalaya
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft
import dev.yacruk.io.core.theme.source.lineHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YaaumBasicTextField(
    modifier: Modifier = Modifier,
    text: String? = null,
    onTextChanged: ((String) -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    textStyle: TextStyle,
    onCleanTextClick: (() -> Unit)? = null,
    strokeWidth: Dp,
    primaryState: YaaumBasicTextFieldState = YaaumBasicTextFieldState.Enabled,
    isDisabled: Boolean = false,
    icon: Int?,
    iconOffset: Dp = 0.dp,
) {
    val focusRequester = remember { FocusRequester() }

    var state by remember {
        mutableStateOf(primaryState)
    }

    val backgroundColor = renkon_beige
    val borderColor = black_mesa
    val borderColorAlt = rustling_leaves
    val hoverColor = stone_craft
    val disableColor = jambalaya

    when {
        isDisabled -> {
            state = YaaumBasicTextFieldState.Disabled
        }

        !isDisabled -> {
            state = YaaumBasicTextFieldState.Enabled
        }
    }

    var textState by remember { mutableStateOf(text) }

    var isOnFocus by remember { mutableStateOf(true) }

    if (isOnFocus) {
        if (isDisabled) {
            state = YaaumBasicTextFieldState.Disabled
        } else {
            state = YaaumBasicTextFieldState.Focused
        }
    } else {
        if (isDisabled) {
            state = YaaumBasicTextFieldState.Disabled
        } else {
            state = YaaumBasicTextFieldState.Enabled
        }
    }

    val borderColorAltState by animateColorAsState(
        targetValue =
        when (state) {
            YaaumBasicTextFieldState.Focused -> borderColorAlt
            YaaumBasicTextFieldState.Disabled -> disableColor
            else -> Color.Transparent
        },
        label = "borderColorAltState",
    )

    val backgroundColorState by animateColorAsState(
        targetValue =
        when (state) {
            YaaumBasicTextFieldState.Disabled -> disableColor
            else -> backgroundColor
        },
        label = "backgroundColorAltState",
    )

    val interactionSource = remember { MutableInteractionSource() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = borderColorAlt
    )

    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .foo(
                strokeWidth = strokeWidth,
                borderColor = borderColor,
                backgroundColor = backgroundColorState,
                borderColorAlt = borderColorAltState,
            )
            .padding(
                start = strokeWidth,
                end = strokeWidth,
                bottom = strokeWidth,
                top = strokeWidth,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        icon?.let {
            Spacer(modifier = Modifier.width(iconOffset))
            Icon(
                painterResource(id = it),
                contentDescription = "",
                modifier = Modifier
                    .size(textStyle.fontSize.value.dp)
                    .padding(all = 0.dp)
            )
        }
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
//                cursorBrush = SolidColor(Color.Transparent),
                cursorBrush = SolidColor(Color.Black),
                modifier =
                modifier
                    .focusRequester(focusRequester)
                    .weight(1f)
                    .onFocusChanged { state ->
                        isOnFocus = state.isFocused
                    }
                    .clearFocusOnKeyboardDismiss(isOnFocus)
                    .background(Color.Cyan),
                value = textState ?: "",
                onValueChange = {
                    onTextChanged?.invoke(it)
                    textState = it

                },

                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                textStyle = textStyle,
                singleLine = singleLine,
                enabled = !isDisabled,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = {
                        text ->
                    val transformedText = if (isOnFocus) {
                        text.text + "_"
                    } else {
                        text.text+" "
                    }

                    // The offset here simply maps the new characters positions.
                    val offsetMapping = object : OffsetMapping {
                        override fun originalToTransformed(offset: Int): Int {
                            // The original offset maps directly unless it's at the end, where the underscore is added.
                            if(offset==0)return 0
                            return if (offset <= text.length) offset else text.length + 2
                        }

                        override fun transformedToOriginal(offset: Int): Int {
                            // The transformed offset maps directly unless it's at the end, where the underscore is.
                            if(offset==0)return 0
                            return if (offset <= text.length) offset else text.length+2
                        }
                    }

                    val numberOffsetTranslator = object : OffsetMapping {
                        override fun originalToTransformed(offset: Int): Int {
                            return offset+1
                        }

                        override fun transformedToOriginal(offset: Int): Int {
                            return offset -1
                        }
                    }

//                    TransformedText(AnnotatedString(transformedText), offsetMapping)
                    TransformedText(AnnotatedString(transformedText), numberOffsetTranslator)
                },
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextFieldDecorationBox(
                            value = text ?: "",
                            innerTextField = innerTextField,
                            singleLine = singleLine,
                            enabled = !isDisabled,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            contentPadding = PaddingValues(
                                horizontal = strokeWidth * 2,
                                vertical = strokeWidth
                            ),
                        )
                    }
                },
            )
        }

//        if(textState?.isEmpty()==false) {
            Icon(
                painterResource(id = R.drawable.icon_check_24),
                contentDescription = "",
                modifier = Modifier
                    .size(textStyle.fontSize.value.dp)
                    .padding(all = 0.dp)
            )
//        }
    }
}


sealed class YaaumBasicTextFieldState {
    data object Enabled : YaaumBasicTextFieldState()

    data object Focused : YaaumBasicTextFieldState()

    data object Disabled : YaaumBasicTextFieldState()
}

