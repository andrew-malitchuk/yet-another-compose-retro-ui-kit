package dev.yacruk.io.components.uikit.demo

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.yacruk.io.R
import dev.yacruk.io.components.uikit.badge.YacrukBadge
import dev.yacruk.io.components.uikit.board.YacrukBorder
import dev.yacruk.io.components.uikit.button.content.YacrukContentButton
import dev.yacruk.io.components.uikit.button.icon.YacrukIconButton
import dev.yacruk.io.components.uikit.button.ordinary.YacrukButton
import dev.yacruk.io.components.uikit.checkbox.YacrukCheckbox
import dev.yacruk.io.components.uikit.chip.YacrukChip
import dev.yacruk.io.components.uikit.field.YaaumBasicTextField
import dev.yacruk.io.components.uikit.label.YacrukLabel
import dev.yacruk.io.components.uikit.progress.YacrukProgressBar
import dev.yacruk.io.components.uikit.slider.YacrukSlider
import dev.yacruk.io.components.uikit.switch.YacrukSwitchButton
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.theme.common.YacrukTheme
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Demo(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(YacrukTheme.spacing.medium),
    ) {
        val coroutineContext = rememberCoroutineScope()
        val context = LocalContext.current
        var enabled by remember {
            mutableStateOf(true)
        }
        val faker = Faker()
        YacrukLabel(
            title = faker.cowboyBebop.quote(),
            textStyle = YacrukTheme.typography.body,
        ) {
            YacrukButton(
                borderWidth = 4.dp,
                icon = {
                    Icon(
                        painterResource(id = R.drawable.icon_check_24),
                        contentDescription = "",
                    )
                },
                text = faker.fmaBrotherhood.cities(),
                isDisabled = enabled,
                onClick = {
                    coroutineContext.launch {
                        Toast.makeText(context, faker.cowboyBebop.episode(), Toast.LENGTH_SHORT).show()
                    }
                },
            )
        }

        var sliderState by remember {
            mutableFloatStateOf(0f)
        }

        YacrukSlider(
            value = sliderState,
            onValueChanged = { sliderState = it },
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            valueRange = 0f..30f,
            stepSize = 2f,
            borderWidth = 4.dp,
            pointerSize = 12.dp,
        )

        YacrukIconButton(
            borderWidth = 4.dp,
            icon = R.drawable.icon_check_24,
            isDisabled = enabled,
            iconSize = 48.dp,
            iconOffset = 2.dp,
            onClick = {
                coroutineContext.launch {
                    Toast.makeText(context, faker.cowboyBebop.episode(), Toast.LENGTH_SHORT).show()
                }
            },
        )
        YacrukContentButton(
            borderWidth = 4.dp,
            isDisabled = enabled,
            contentSize = 48.dp,
            contentOffset = 2.dp,
            onClick = {
                coroutineContext.launch {
                    Toast.makeText(context, faker.cowboyBebop.episode(), Toast.LENGTH_SHORT).show()
                }
            },
            content = {
                YacrukText(text = faker.idNumber.invalid(), textStyle = YacrukTheme.typography.headline)
            },
        )

        YaaumBasicTextField(
            borderWidth = 4.dp,
            isDisabled = enabled,
            textStyle = YacrukTheme.typography.headline,
            iconOffset = 4.dp,
            leadingIcon = R.drawable.icon_check_24,
            tailingIcon = R.drawable.icon_times_circle_24,
        )

        YacrukChip(
            borderWidth = 4.dp,
            isDisabled = enabled,
            iconOffset = 4.dp,
            text = faker.fmaBrotherhood.cities(),
            textStyle = YacrukTheme.typography.headline,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.icon_check_24),
                    contentDescription = "",
                )
            },
        )
        YacrukCheckbox(
            borderWidth = 4.dp,
            textStyle = YacrukTheme.typography.headline,
            iconSize = 24.dp,
            text = faker.fmaBrotherhood.cities(),
            textSpacing = 4.dp,
            isDisabled = enabled,
        )
        YacrukBorder(
            textStyle = YacrukTheme.typography.body,
            borderWidth = 4.dp,
            text = faker.fmaBrotherhood.cities(),
            padding = 8.dp,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(YacrukTheme.spacing.medium),
            ) {
                YacrukBadge(
                    text = faker.idNumber.invalid(),
                    textStyle = YacrukTheme.typography.headline,
                    padding = 4.dp,
                    shape = RectangleShape,
                )

                YacrukSwitchButton(
                    borderWidth = 4.dp,
                    thumbSize = 24.dp,
                    initValue = enabled,
                    onStateChange = {
                        enabled = it
                    },
                )
                YacrukProgressBar(
                    modifier = Modifier.fillMaxWidth(),
                    progress = 0.5f,
                    height = 8.dp,
                )
            }
        }
    }
}
