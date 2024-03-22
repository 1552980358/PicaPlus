package me.ks.chan.pica.plus.ui.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.ks.chan.pica.plus.ui.theme.PicaPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicaPlusTheme {
            }
        }
    }
}