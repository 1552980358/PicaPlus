package me.ks.chan.pica.plus.ui.theme

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun colorScheme(
    useDynamicColors: Boolean,
    isDarkTheme: Boolean,
): ColorScheme {
    return when {
        useDynamicColors && SupportDynamicColors -> {
            dynamicColorScheme(
                isDarkTheme = isDarkTheme,
                context = LocalContext.current
            )
        }
        isDarkTheme -> { darkColorScheme() }
        else -> { lightColorScheme() }
    }
}

private val SupportDynamicColors: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@RequiresApi(Build.VERSION_CODES.S)
private fun dynamicColorScheme(
    isDarkTheme: Boolean,
    context: Context,
    dynamicColorScheme: (Context) -> ColorScheme = when {
        isDarkTheme -> ::dynamicDarkColorScheme
        else -> ::dynamicLightColorScheme
    }
): ColorScheme {
    return dynamicColorScheme(context)
}