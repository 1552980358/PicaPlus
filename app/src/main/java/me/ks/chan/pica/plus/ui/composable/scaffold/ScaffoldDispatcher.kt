package me.ks.chan.pica.plus.ui.composable.scaffold

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

val LocalScaffoldDispatcher = compositionLocalOf(
    defaultFactory = ::ScaffoldDispatcher
)

class ScaffoldDispatcher {

    private var navBarItemSecondaryClick: (() -> Unit)? = null
    private var topAppBarActions by mutableStateOf<(@Composable RowScope.() -> Unit)?>(null)

    fun onNavBarItemSecondaryClick() {
        navBarItemSecondaryClick?.invoke()
    }

    @Composable
    fun TopAppBarActions(rowScope: RowScope) {
        topAppBarActions?.invoke(rowScope)
    }

    @Composable
    fun NavBarItemSecondaryClick(action: () -> Unit) {
        DisposableEffect(key1 = Unit) {
            navBarItemSecondaryClick = action
            onDispose {
                if (navBarItemSecondaryClick == action) {
                    navBarItemSecondaryClick = null
                }
            }
        }
    }

    @Composable
    fun TopAppBarActions(actions: @Composable RowScope.() -> Unit) {
        DisposableEffect(key1 = Unit) {
            topAppBarActions = actions
            onDispose {
                if (topAppBarActions == actions) {
                    topAppBarActions = null
                }
            }
        }
    }

}

@Composable
fun ProvideNavBarItemSecondaryClick(action: () -> Unit) {
    LocalScaffoldDispatcher.current
        .NavBarItemSecondaryClick(action)
}

@Composable
fun ProvideTopAppBarActions(actions: @Composable RowScope.() -> Unit) {
    LocalScaffoldDispatcher.current
        .TopAppBarActions(actions)
}