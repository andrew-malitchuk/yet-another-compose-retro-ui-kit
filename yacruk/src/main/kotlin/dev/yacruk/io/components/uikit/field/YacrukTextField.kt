package dev.yacruk.io.components.uikit.field

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.R
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.core.ext.clearFocusOnKeyboardDismiss
import dev.yacruk.io.core.ext.noRippleClickable
import dev.yacruk.io.core.ext.yacrukBorder
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.jambalaya
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves

/**
 * A composable that implements a basic text field with customizable appearance and behavior.
 *
 * This composable uses the Material Design `BasicTextField` under the hood with extensive
 * configuration options.
 *
 * @OptIn(ExperimentalMaterialApi::class) This function uses an experimental Material API.
 *
 * @param modifier [Optional] Modifier to apply to the text field. Defaults to an empty Modifier.
 * @param text The current text content of the field. Can be null.
 * @param onTextChanged A callback lambda invoked when the text content changes.
 * @param keyboardOptions Options that specify the type of keyboard and its behavior. Defaults to KeyboardOptions.Default.
 * @param keyboardActions Actions to be performed when specific keys are pressed on the software keyboard. Defaults to KeyboardActions.Default.
 * @param singleLine Whether the text field should allow for only a single line of text. Defaults to true.
 * @param maxLines The maximum number of lines to display for the text field. Defaults to 1 if singleLine is true, otherwise Int.MAX_VALUE.
 * @param minLines The minimum number of lines to display for the text field. Defaults to 1.
 * @param textStyle The text style to apply to the text content.
 * @param onCleanTextClick A callback lambda invoked when the clear text icon is clicked.
 * @param borderWidth The width of the border around the text field in dp.
 * @param primaryState The initial state of the text field (Enabled, Focused, or Disabled). Defaults to YaaumBasicTextFieldState.Enabled.
 * @param isDisabled Whether the text field is currently disabled for user interaction. Defaults to false.
 * @param leadingIcon Resource ID for an icon to be displayed at the beginning of the text field. Can be null.
 * @param tailingIcon Resource ID for an icon to be displayed at the end of the text field. Can be null.
 * @param iconOffset The horizontal offset for the leading and trailing icons in dp. Defaults to 0.dp.
 * @param colors The color scheme for the text field's various states (background, border, etc.). Defaults to the colors defined in `YaaumBasicTextFieldColorsDefaults.colors()`.
 */
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
    borderWidth: Dp,
    primaryState: YaaumBasicTextFieldState = YaaumBasicTextFieldState.Enabled,
    isDisabled: Boolean = false,
    leadingIcon: Int?,
    tailingIcon: Int?,
    iconOffset: Dp = 0.dp,
    colors: YaaumBasicTextFieldColors = YaaumBasicTextFieldColorsDefaults.colors(),
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "text" to text,
                "onTextChanged" to onTextChanged,
                "keyboardOptions" to keyboardOptions,
                "keyboardActions" to keyboardActions,
                "singleLine" to singleLine,
                "maxLines" to maxLines,
                "minLines" to minLines,
                "textStyle" to textStyle,
                "onCleanTextClick" to onCleanTextClick,
                "borderWidth" to borderWidth,
                "primaryState" to primaryState,
                "isDisabled" to isDisabled,
                "leadingIcon" to leadingIcon,
                "tailingIcon" to tailingIcon,
                "iconOffset" to iconOffset,
                "colors" to colors,
            ),
    )

    val focusRequester = rememberSaveable { FocusRequester() }

    var state by rememberSaveable {
        mutableStateOf(primaryState)
    }

    when {
        isDisabled ->
            state = YaaumBasicTextFieldState.Disabled

        !isDisabled ->
            state = YaaumBasicTextFieldState.Enabled
    }

    var textState by rememberSaveable { mutableStateOf(text) }

    var isOnFocus by rememberSaveable { mutableStateOf(true) }

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
                YaaumBasicTextFieldState.Focused -> colors.borderColorAlt
                YaaumBasicTextFieldState.Disabled -> colors.disableColor
                else -> Color.Transparent
            },
        label = "borderColorAltState",
    )

    val backgroundColorState by animateColorAsState(
        targetValue =
            when (state) {
                YaaumBasicTextFieldState.Disabled -> colors.disableColor
                else -> colors.backgroundColor
            },
        label = "backgroundColorAltState",
    )

    val interactionSource = rememberSaveable { MutableInteractionSource() }
    val customTextSelectionColors =
        TextSelectionColors(
            handleColor = Color.Transparent,
            backgroundColor = colors.borderColorAlt,
        )

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .yacrukBorder(
                    borderWidth = borderWidth,
                    borderColor = colors.borderColor,
                    backgroundColor = backgroundColorState,
                    borderColorAlt = borderColorAltState,
                )
                .padding(
                    start = borderWidth,
                    end = borderWidth,
                    bottom = borderWidth,
                    top = borderWidth,
                ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        leadingIcon?.let {
            Spacer(modifier = Modifier.width(iconOffset))
            Icon(
                painterResource(id = it),
                contentDescription = "",
                modifier =
                    Modifier
                        .size(textStyle.fontSize.value.dp)
                        .padding(all = 0.dp),
            )
        }
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                cursorBrush = SolidColor(Color.Transparent),
                modifier =
                    modifier
                        .focusRequester(focusRequester)
                        .weight(1f)
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
                textStyle = textStyle,
                singleLine = singleLine,
                enabled = !isDisabled,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = { text ->
                    val transformedText =
                        if (isOnFocus) {
                            text.text + "_"
                        } else {
                            text.text + " "
                        }

                    val numberOffsetTranslator =
                        object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 1
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return offset - 1
                            }
                        }

                    TransformedText(AnnotatedString(transformedText), numberOffsetTranslator)
                },
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        TextFieldDecorationBox(
                            value = text ?: "",
                            innerTextField = innerTextField,
                            singleLine = singleLine,
                            enabled = !isDisabled,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            contentPadding =
                                PaddingValues(
                                    horizontal = borderWidth * 2,
                                    vertical = borderWidth,
                                ),
                        )
                    }
                },
            )
        }

        tailingIcon?.let {
            if (textState?.isEmpty() == false) {
                Icon(
                    painterResource(id = it),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .size(textStyle.fontSize.value.dp)
                            .noRippleClickable {
                                textState = ""
                                onCleanTextClick?.invoke()
                            }
                            .padding(all = 0.dp),
                )
                Spacer(modifier = Modifier.width(iconOffset))
            }
        }
    }
}

sealed class YaaumBasicTextFieldState {
    data object Enabled : YaaumBasicTextFieldState()

    data object Focused : YaaumBasicTextFieldState()

    data object Disabled : YaaumBasicTextFieldState()
}

class YaaumBasicTextFieldColors internal constructor(
    val backgroundColor: Color,
    val borderColor: Color,
    val borderColorAlt: Color,
    val disableColor: Color,
)

object YaaumBasicTextFieldColorsDefaults {
    @Composable
    fun colors(
        backgroundColor: Color = renkon_beige,
        borderColor: Color = black_mesa,
        borderColorAlt: Color = rustling_leaves,
        disableColor: Color = jambalaya,
    ) = YaaumBasicTextFieldColors(
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        borderColorAlt = borderColorAlt,
        disableColor = disableColor,
    )
}

@YacrukPreview
@Composable
private fun PreviewYaaumBasicTextField() {
    YacrukTheme {
        YaaumBasicTextField(
            borderWidth = 4.dp,
            textStyle = YacrukTheme.typography.headline,
            iconOffset = 4.dp,
            leadingIcon = R.drawable.icon_check_24,
            tailingIcon = R.drawable.icon_times_circle_24,
        )
    }
}
