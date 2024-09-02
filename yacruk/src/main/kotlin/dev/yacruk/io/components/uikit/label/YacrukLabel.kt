package dev.yacruk.io.components.uikit.label

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.theapache64.rebugger.Rebugger
import dev.yacruk.io.components.internal.preview.YacrukPreview
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import io.github.serpro69.kfaker.Faker

/**
 * A composable function that displays a labeled section with a title, optional padding,
 * and a content slot for additional composables.
 *
 * @param modifier [Optional] Modifier to apply to the label composable. Defaults to an empty Modifier.
 * @param title The text displayed as the label title.
 * @param padding [Optional] The spacing between the title and the content in dp. Defaults to YacrukTheme.spacing.small.
 * @param textStyle The text style to apply to the title.
 * @param color [Optional] The text color for the title. Defaults to Color.Unspecified.
 * @param content A lambda composable that defines the content to be placed below the title.
 */
@Composable
fun YacrukLabel(
    modifier: Modifier = Modifier,
    title: String,
    padding: Dp = YacrukTheme.spacing.small,
    textStyle: TextStyle,
    color: Color = Color.Unspecified,
    content: @Composable () -> Unit,
) {
    Rebugger(
        trackMap =
            mapOf(
                "modifier" to modifier,
                "title" to title,
                "padding" to padding,
                "textStyle" to textStyle,
                "color" to color,
                "content" to content,
            ),
    )

    Column(
        modifier =
            modifier
                .wrapContentHeight(),
    ) {
        YacrukText(text = title, textStyle = textStyle, color = color)
        Spacer(modifier = Modifier.height(padding))
        content()
    }
}

/**
 * A variant of the `YacrukLabel` composable that retrieves the title text from a string resource.
 *
 * This function offers the same behavior for `padding`, `textStyle`, `color`, and `content` as the original `YacrukLabel` function. Refer to its documentation for detailed information about these parameters.
 *
 * @param modifier [Optional] Modifier to apply to the label composable. Defaults to an empty Modifier.
 * @param titleResId The resource ID for the string to be displayed as the label title.
 * @param padding [Optional] The spacing between the title and the content in dp. Defaults to YacrukTheme.spacing.small.
 * @param textStyle The text style to apply to the title.
 * @param color [Optional] The text color for the title. Defaults to Color.Unspecified.
 * @param content A lambda composable that defines the content to be placed below the title.
 */
@Composable
fun YacrukLabel(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    padding: Dp = YacrukTheme.spacing.small,
    textStyle: TextStyle,
    color: Color = Color.Unspecified,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
            modifier
                .wrapContentHeight(),
    ) {
        YacrukText(textResId = titleResId, textStyle = textStyle, color = color)
        Spacer(modifier = Modifier.height(padding))
        content()
    }
}

@YacrukPreview
@Composable
private fun PreviewYacrukLabel() {
    val faker = Faker()
    YacrukTheme {
        YacrukLabel(
            title = faker.cowboyBebop.quote(),
            textStyle = YacrukTheme.typography.body,
        ) {
        }
    }
}
