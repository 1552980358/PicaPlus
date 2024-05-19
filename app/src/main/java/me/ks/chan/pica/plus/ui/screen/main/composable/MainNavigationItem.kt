package me.ks.chan.pica.plus.ui.screen.main.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import me.ks.chan.pica.plus.ui.composable.scaffold.LocalScaffoldDispatcher
import me.ks.chan.pica.plus.ui.screen.main.model.MainNavigation

@Composable
fun RowScope.MainNavigationItem(
    mainNavigation: MainNavigation,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    selected: Boolean =
        navBackStackEntry?.destination?.route == mainNavigation.route,
) {
    val scaffoldDispatcher = LocalScaffoldDispatcher.current

    val label = stringResource(id = mainNavigation.labelResId)

    NavigationBarItem(
        selected = selected,
        label = {
            Text(text = label)
        },
        icon = {
            Icon(
                imageVector = when {
                    selected -> mainNavigation.filledIcon
                    else -> mainNavigation.outlinedIcon
                },
                contentDescription = label,
            )
        },
        onClick = {
            when {
                selected -> {
                    scaffoldDispatcher.onNavBarItemSecondaryClick()
                }
                else -> {
                    navController.navigate(mainNavigation.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    )
}