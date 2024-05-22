package me.ks.chan.pica.plus.ui.nav

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.ks.chan.pica.plus.ui.screen.greeting.GreetingPreview
import me.ks.chan.pica.plus.ui.screen.greeting.GreetingScreen
import me.ks.chan.pica.plus.ui.screen.main.Main
import me.ks.chan.pica.plus.ui.screen.register.RegisterPreview
import me.ks.chan.pica.plus.ui.screen.register.RegisterScreen
import me.ks.chan.pica.plus.ui.screen.sign_in.SignInPreview
import me.ks.chan.pica.plus.ui.screen.sign_in.SignInScreen
import me.ks.chan.pica.plus.ui.theme.Duration_Long2

const val Guest = "guest"
const val SignIn = "sign_in"
const val Register = "register"
const val Greeting = "greeting"

private const val SlideOffsetDivider = 8

fun NavGraphBuilder.guestNav(navController: NavHostController) {
    navigation(route = Guest, startDestination = SignIn) {
        composable(
            route = SignIn,
            enterTransition = {
                when (initialState.destination.route) {
                    Register -> {
                        slideInVertically (
                            animationSpec = tween(
                                durationMillis = Duration_Long2
                            ),
                            initialOffsetY = { -it / SlideOffsetDivider }
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = Duration_Long2
                            )
                        )
                    }
                    else -> {
                        fadeIn(
                            animationSpec = tween(durationMillis = Duration_Long2)
                        )
                    }
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Register -> {
                        slideOutVertically(
                            animationSpec = tween(
                                durationMillis = Duration_Long2
                            ),
                            targetOffsetY = { -it / SlideOffsetDivider }
                        ) + fadeOut(
                            animationSpec = tween(
                                durationMillis = Duration_Long2
                            )
                        )
                    }
                    else -> {
                        fadeOut(
                            animationSpec = tween(durationMillis = Duration_Long2)
                        )
                    }
                }
            },
        ) {
            SignInScreen(
                onSignInSuccess = { navController.navigate(Greeting) },
                onForgotPassword = { /*TODO*/ },
                onCreateAccount = { navController.navigate(Register) }
            )
        }

        composable(
            route = Register,
            enterTransition = {
                slideIntoContainer(
                    towards = SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    initialOffset = { it * SlideOffsetDivider.dec() / SlideOffsetDivider }
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = SlideDirection.Down,
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    )
                )
            }
        ) {
            RegisterScreen(
                onExit = navController::navigateUp
            )
        }

        composable(
            route = Greeting,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(durationMillis = Duration_Long2)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(durationMillis = Duration_Long2)
                )
            },
        ) {
            GreetingScreen(
                onSuccess = {
                    navController.navigate(Main) {
                        popUpTo(Guest) {
                            inclusive = true
                        }
                    }
                },
                onError = {
                    navController.navigate(SignIn) {
                        popUpTo(Greeting) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.guestNavPreview(
    navController: NavHostController
) {
    navigation(route = Guest, startDestination = SignIn) {
        composable(route = SignIn) {
            SignInPreview(
                onSignInSuccess = { navController.navigate(Greeting) },
                onForgotPassword = { /*TODO*/ },
                onCreateAccount = {
                    navController.navigate(Register)
                }
            )
        }

        composable(route = Register) {
            RegisterPreview(
                onExit = { navController.navigate(SignIn) }
            )
        }

        composable(route = Greeting) {
            GreetingPreview(
                onSuccess = { navController.navigate(Main) },
                onError = { navController.navigate(SignIn) }
            )
        }
    }
}