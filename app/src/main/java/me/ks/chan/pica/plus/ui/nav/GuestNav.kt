package me.ks.chan.pica.plus.ui.nav

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

const val Guest = "guest"
const val SignIn = "sign_in"
const val Register = "register"
const val Greeting = "greeting"

fun NavGraphBuilder.guestNav(navController: NavHostController) {
    navigation(route = Guest, startDestination = SignIn) {
        composable(route = SignIn) {
            SignInScreen(
                onSignInSuccess = { navController.navigate(Greeting) },
                onForgotPassword = { /*TODO*/ },
                onCreateAccount = { navController.navigate(Register) }
            )
        }

        composable(route = Register) {
            RegisterScreen(
                onExit = navController::navigateUp
            )
        }

        composable(route = Greeting) {
            GreetingScreen(
                onSuccess = {
                    navController.navigate(Main) {
                        popUpTo(Greeting) {
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