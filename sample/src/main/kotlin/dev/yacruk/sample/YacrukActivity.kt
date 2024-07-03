package dev.yacruk.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
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
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.black_mesa
import dev.yacruk.io.core.theme.source.color.jambalaya
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.io.core.theme.source.color.stone_craft
import dev.yacruk.io.core.theme.source.color.true_navy
import kotlinx.coroutines.launch

class YacrukActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coroutineContext = rememberCoroutineScope()
            val context = LocalContext.current

            YacrukTheme {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(
                            renkon_beige
                        )
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .padding(YacrukTheme.spacing.medium)
                ) {
                    var foo by remember {
                        mutableStateOf(true)
                    }
                    YacrukLabel(
                        title = "foobar",
                        textStyle = YacrukTheme.typography.body,
                    ) {
                        YacrukButton(
                            borderWidth = 4.dp,
                            icon = {
                                Icon(
                                    painterResource(id = R.drawable.icon_check_24),
                                    contentDescription = ""
                                )
                            },
                            text = "foobarfoobarfoobarfoobarfoobarfoobar",
                            isDisabled = foo,
                            onClick = {
                                coroutineContext.launch {
                                    Toast.makeText(context, "foo", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                    Switch(checked = foo, onCheckedChange = {
                        foo = it
                    })

                    var foobar by remember {
                        mutableFloatStateOf(0f)
                    }

                    YacrukSlider(
                        value = foobar,
                        onValueChanged = { foobar = it },
                        modifier = Modifier
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
                        isDisabled = foo,
                        iconSize = 48.dp,
                        iconOffset = 2.dp,
                        onClick = {
                            coroutineContext.launch {
                                Toast.makeText(context, "foo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    YacrukContentButton(
                        borderWidth = 4.dp,
                        isDisabled = foo,
                        contentSize = 48.dp,
                        contentOffset = 2.dp,
                        onClick = {
                            coroutineContext.launch {
                                Toast.makeText(context, "foo", Toast.LENGTH_SHORT).show()
                            }
                        },
                        content = {
                            YacrukText(text = "10", textStyle = YacrukTheme.typography.headline)
                        }
                    )

                    YaaumBasicTextField(
                        borderWidth = 4.dp,
                        isDisabled = foo,
                        textStyle = YacrukTheme.typography.headline,
                        iconOffset = 4.dp,
                        leadingIcon = R.drawable.icon_check_24,
                        tailingIcon = R.drawable.icon_times_circle_24,
                    )

                    YacrukChip(
                        borderWidth = 4.dp,
                        isDisabled = foo,
                        iconOffset = 4.dp,
                        text = "foo",
                        textStyle = YacrukTheme.typography.headline,
                        leadingIcon = {
                            Icon(
                                painterResource(id = R.drawable.icon_check_24),
                                contentDescription = ""
                            )
                        },
                    )
                    YacrukCheckbox(
                        borderWidth = 4.dp,
                        textStyle = YacrukTheme.typography.headline,
                        iconSize = 24.dp,
                        text = "foobar",
                        textSpacing = 4.dp,
                        isDisabled = foo,
                    )
                    YacrukBorder(
                        textStyle = YacrukTheme.typography.body,
                        borderWidth = 4.dp,
                        text = "lorem ipsum",
                        padding = 8.dp,
                    ) {
                        Column {
                            YacrukBadge(
                                text = "1",
                                textStyle = YacrukTheme.typography.headline,
                                padding = 4.dp,
                                shape = RectangleShape
                            )
                            YacrukSwitchButton(
                                borderWidth = 4.dp,
                                thumbSize = 24.dp,
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
        }

    }
}
