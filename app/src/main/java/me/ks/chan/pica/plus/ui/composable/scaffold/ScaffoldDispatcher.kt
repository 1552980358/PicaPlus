package me.ks.chan.pica.plus.ui.composable.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf

val LocalScaffoldDispatcher = compositionLocalOf(
    defaultFactory = ::ScaffoldDispatcher
)

class ScaffoldDispatcher {

    private var navBarItemSecondaryClick: (() -> Unit)? = null

    fun onNavBarItemSecondaryClick() {
        navBarItemSecondaryClick?.invoke()
    }

    @Composable
    fun NavBarItemSecondaryClick(action: () -> Unit) {
        DisposableEffect(key1 = Unit) {
            navBarItemSecondaryClick = action
            onDispose {
                navBarItemSecondaryClick = null
            }
        }
    }

}

@Composable
fun ProvideNavBarItemSecondaryClick(action: () -> Unit) {
    LocalScaffoldDispatcher.current
        .NavBarItemSecondaryClick(action)
}