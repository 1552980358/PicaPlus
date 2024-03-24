package me.ks.chan.pica.plus.ui.theme

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun PicaPlusTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    colorScheme: ColorScheme = colorScheme(
        useDynamicColors = useDynamicColors, isDarkTheme = isDarkTheme,
    ),
    content: @Composable () -> Unit
) {
    ProductionSideEffect { view ->
        setupSystemBars(view = view, isDarkTheme = isDarkTheme)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

@Composable
private fun ProductionSideEffect(
    view: View = LocalView.current, sideEffect: (View) -> Unit,
) {
    if (!view.isInEditMode) {
        SideEffect { sideEffect(view) }
    }
}

private fun setupSystemBars(
    view: View,
    isDarkTheme: Boolean,
    window: Window = (view.context as Activity).window
) {
    with(WindowCompat.getInsetsController(window, view)) {
        // Appearance (icons, text)
        isAppearanceLightStatusBars = !isDarkTheme
        isAppearanceLightNavigationBars = !isDarkTheme
    }
}