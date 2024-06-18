package dev.yacruk.sample

import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yacruk.io.R
import dev.yacruk.io.components.uikit.button.YacrukButton
import dev.yacruk.io.components.uikit.label.YacrukLabel
import dev.yacruk.io.components.uikit.text.YacrukText
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.renkon_beige
import dev.yacruk.io.core.theme.source.color.rustling_leaves
import dev.yacruk.sample.ui.theme.YACRUKTheme
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class YacrukActivity : ComponentActivity() {
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