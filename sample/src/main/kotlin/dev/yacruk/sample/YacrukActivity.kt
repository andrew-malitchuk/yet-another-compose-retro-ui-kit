package dev.yacruk.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import dev.yacruk.io.components.uikit.demo.Demo
import dev.yacruk.io.core.theme.common.YacrukTheme
import dev.yacruk.io.core.theme.source.YacrukTheme
import dev.yacruk.io.core.theme.source.color.renkon_beige

class YacrukActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                    Demo()
                }
            }
        }

    }
}
