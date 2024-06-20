package dev.yacruk.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yacruk.io.R
import dev.yacruk.io.components.uikit.button.content.YacrukContentButton
import dev.yacruk.io.components.uikit.button.icon.YacrukIconButton
import dev.yacruk.io.components.uikit.button.ordinary.YacrukButton
import dev.yacruk.io.components.uikit.label.YacrukLabel
import dev.yacruk.io.components.uikit.slider.YacrukSlider
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.sample.ui.theme.YACRUKTheme
import kotlinx.coroutines.launch

class YacrukActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
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
                            strokeWidth = 4.dp,
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
                        strokeWidth = 4.dp,
                        pointerSize = 12.dp,
                    )

                    YacrukIconButton(
                        strokeWidth = 4.dp,
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
                        strokeWidth = 4.dp,
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
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YACRUKTheme {
        Greeting("Android")
    }
}