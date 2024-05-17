package me.ks.chan.pica.plus.ui.screen.main

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.ks.chan.pica.plus.ui.screen.categroy.CategoryPreview
import me.ks.chan.pica.plus.ui.screen.categroy.CategoryScreen
import me.ks.chan.pica.plus.ui.screen.home.HomePreview
import me.ks.chan.pica.plus.ui.screen.home.HomeScreen
import me.ks.chan.pica.plus.ui.screen.main.composable.MainNavigationItem
import me.ks.chan.pica.plus.ui.screen.main.model.MainNavigation
import me.ks.chan.pica.plus.ui.theme.Transition_200
import me.ks.chan.pica.plus.util.compose.FalseState

const val Main = "main"
const val Home = "home"
const val Category = "category"

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    MainContent(navController = navController) {
        composable(route = Home) {
            HomeScreen()
        }
        composable(route = Category) {
            CategoryScreen()
        }
    }
}

private const val BottomBar = "BottomBar"
private const val BottomPadding = "BottomPadding"


@Composable
private fun MainContent(
    navController: NavHostController,
    startDestination: String = Home,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
) {
    var navigationBarSlideUp by remember(::FalseState)

    LaunchedEffect(key1 = Unit) {
        navigationBarSlideUp = true
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = navigationBarSlideUp,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = Transition_200, easing = LinearEasing),
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = Transition_200, easing = LinearEasing),
                ),
                label = BottomBar,
            ) {
                NavigationBar {
                    MainNavigation.entries
                        .forEach { mainNavigation ->
                            MainNavigationItem(
                                navController = navController,
                                mainNavigation = mainNavigation,
                            )
                        }
                }
            }
        }
    ) { outerPaddings ->
        val bottomPadding by animateDpAsState(
            targetValue = outerPaddings.calculateBottomPadding(),
            animationSpec = tween(durationMillis = Transition_200, easing = LinearEasing),
            label = BottomPadding,
        )

        NavHost(
            modifier = Modifier.padding(bottom = bottomPadding),
            navController = navController,
            startDestination = startDestination,
            builder = navGraphBuilder,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    val context = LocalContext.current
    if (context is ComponentActivity) {
        context.enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            )
        )
    }

    MainContent(
        navController = rememberNavController(),
        startDestination = Home,
        navGraphBuilder = {
            composable(route = Home) {
                HomePreview()
            }

            composable(route = Category) {
                CategoryPreview()
            }
        },
    )
}
