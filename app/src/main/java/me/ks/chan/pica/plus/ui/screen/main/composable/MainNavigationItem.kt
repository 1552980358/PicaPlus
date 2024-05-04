package me.ks.chan.pica.plus.ui.screen.main.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import me.ks.chan.pica.plus.ui.screen.main.model.MainNavigation

@Composable
fun RowScope.MainNavigationItem(
    navController: NavController,
    mainNavigation: MainNavigation,
    selected: Boolean =
        navController.currentDestination?.route == mainNavigation.route,
) {
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
        onClick = {}
    )
}