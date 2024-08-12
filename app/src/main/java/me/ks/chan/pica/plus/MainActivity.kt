package me.ks.chan.pica.plus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.ks.chan.pica.plus.ui.theme.PicaPlusTheme
import me.ks.chan.pica.plus.ui.theme.configureActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureActivity()
        setContent {
            PicaPlusTheme {
            }
        }
    }
}