package com.example.thread.ui.navigation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.secondary.login.LoginScreen
import com.example.thread.ui.screen.secondary.signup.CodeConfirmationScreen
import com.example.thread.ui.screen.secondary.signup.NameAndPasswordScreen
import com.example.thread.ui.screen.secondary.signup.PickUsername
import com.example.thread.ui.screen.secondary.signup.SignUpScreen

enum class LoginDestination(val route: String) {
    LOGIN(getNavRoute(NavigationType.SECONDARY, "login")),
    SIGN_UP(getNavRoute(NavigationType.SECONDARY, "signUp")),
    CODE_CONFIRMATION(getNavRoute(NavigationType.SECONDARY, "codeConfirmation")),
    NAME_PASSWORD(getNavRoute(NavigationType.SECONDARY, "nameAndPassword")),
    PICK_USERNAME(getNavRoute(NavigationType.SECONDARY, "changeUsername"))
}

fun NavGraphBuilder.loginNavGraph(threadNavController: ThreadNavController) {
    composable(LoginDestination.LOGIN.route) {
        LoginScreen(threadNavController = threadNavController)
    }
    composable(LoginDestination.SIGN_UP.route) {
        SignUpScreen(threadNavController = threadNavController)
    }
    composable(LoginDestination.CODE_CONFIRMATION.route) {
        CodeConfirmationScreen(threadNavController = threadNavController)
    }
    composable(LoginDestination.NAME_PASSWORD.route) {
        NameAndPasswordScreen(threadNavController = threadNavController)
    }
    composable(LoginDestination.PICK_USERNAME.route) {
        PickUsername(threadNavController = threadNavController)
    }
}
