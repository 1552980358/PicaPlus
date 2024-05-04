package me.ks.chan.pica.plus.ui.screen.main.model

import androidx.compose.ui.graphics.vector.ImageVector
import me.ks.chan.pica.plus.ui.icon.filled.Home as HomeFilled
import me.ks.chan.pica.plus.ui.icon.filled.Category as CategoryFilled
import me.ks.chan.pica.plus.ui.icon.round.Category as CategoryOutlined
import me.ks.chan.pica.plus.ui.icon.round.Home as HomeOutlined
import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.screen.main.Category as CategoryRoute
import me.ks.chan.pica.plus.ui.screen.main.Home as HomeRoute

enum class MainNavigation(
    val route: String,
    @StringRes
    val labelResId: Int,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
) {

    Home(
        route = HomeRoute,
        labelResId = R.string.screen_main_navigation_home,
        outlinedIcon = HomeOutlined,
        filledIcon = HomeFilled,
    ),

    Category(
        route = CategoryRoute,
        labelResId = R.string.screen_main_navigation_category,
        outlinedIcon = CategoryOutlined,
        filledIcon = CategoryFilled,
    )

}