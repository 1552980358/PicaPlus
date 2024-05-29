package me.ks.chan.pica.plus.ui.nav

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import me.ks.chan.pica.plus.ui.screen.comic.ComicScreen
import me.ks.chan.pica.plus.ui.theme.Duration_Long2
import me.ks.chan.pica.plus.ui.theme.FadingAlpha
import me.ks.chan.pica.plus.ui.theme.slideOffset
import me.ks.chan.pica.plus.util.androidx.navigation.args

private const val ComicId = "comic"
const val ComicNav = "comic_nav/{$ComicId}"

fun NavController.navigateToComic(comic: String) {
    navigate(ComicNav.args(ComicId to comic)) {
        popUpTo(ComicNav) {
            saveState = true
            inclusive = true
        }
        restoreState = true
    }
}

fun NavGraphBuilder.comicNav(navController: NavHostController) {
    navigation(
        route = ComicNav,
        startDestination = Comic,
        arguments = listOf(
            navArgument(ComicId) { type = NavType.StringType },
        ),
        enterTransition = {
            slideInVertically(
                animationSpec = tween(
                    durationMillis = Duration_Long2,
                ),
                initialOffsetY = { it.slideOffset },
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = Duration_Long2,
                ),
                initialAlpha = FadingAlpha,
            )
        },
        exitTransition = {
            slideOutVertically(
                animationSpec = tween(
                    durationMillis = Duration_Long2,
                ),
                targetOffsetY = { it.slideOffset },
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = Duration_Long2,
                )
            )
        }
    ) {
        comic(navController = navController)
    }
}

private val NavController.comicArgument: String?
     @Composable get() = remember {
         runCatching { getBackStackEntry(ComicNav) }
             .getOrNull()
             ?.arguments
             ?.getString(ComicId)
             ?.takeIf(String::isNotBlank)
     }

const val Comic = "comic"
private fun NavGraphBuilder.comic(navController: NavHostController) {
    composable(
        route = Comic,
    ) {
        val comic = navController.comicArgument
        if (comic != null) {
            ComicScreen(
                comic = comic,
                exit = navController::popBackStack,
            )
        }
        BackHandler(
            onBack = navController::popBackStack
        )
    }
}