package me.ks.chan.pica.plus.ui.activity.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.ui.activity.main.viewmodel.MainState
import me.ks.chan.pica.plus.ui.nav.Greeting
import me.ks.chan.pica.plus.ui.nav.Guest
import me.ks.chan.pica.plus.ui.nav.guestNav
import me.ks.chan.pica.plus.ui.nav.guestNavPreview
import me.ks.chan.pica.plus.ui.screen.main.Main
import me.ks.chan.pica.plus.ui.screen.main.MainPreview
import me.ks.chan.pica.plus.ui.screen.main.MainScreen
import me.ks.chan.pica.plus.ui.theme.Duration_Long2
import me.ks.chan.pica.plus.ui.theme.PicaPlusTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )

        val viewModel: MainViewModel by viewModels()
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.state.value !is MainState.Result
        }

        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            MainContent(
                state = state,
            )
        }
        viewModel.splashSetup(this)
    }

}

@Composable
private fun MainContent(
    state: MainState,
    navController: NavHostController = rememberNavController(),
) {
    LaunchedEffect(key1 = state) {
        if (state is MainState.Token) {
            PicaRepository.authorization(state.token)
            if (PicaRepository.isAuthorized) {
                navController.navigate(Greeting)
            }
        }
    }

    PicaPlusTheme {
        MainContent(navController = navController) {
            guestNav(navController)

            composable(
                route = Main,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = Duration_Long2
                        )
                    )
                },
            ) {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainContent(
    navController: NavHostController,
    startDestination: String = Guest,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = navGraphBuilder,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview(
    navController: NavHostController = rememberNavController(),
) {
    MainContent(navController = navController) {
        guestNavPreview(navController = navController)

        composable(route = Main) {
            MainPreview()
        }
    }
}