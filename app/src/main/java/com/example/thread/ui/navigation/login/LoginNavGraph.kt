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
import com.example.thread.ui.screen.secondary.signup.EnterEmailScreen
import com.example.thread.ui.screen.secondary.signup.NewPasswordScreen
import com.example.thread.ui.screen.secondary.signup.SignUpViewModelProvider

enum class LoginDestination(val route: String) {
    LOGIN(getNavRoute(NavigationType.SECONDARY, "login")),
    SIGN_UP(getNavRoute(NavigationType.SECONDARY, "signUp")),
    SIGN_UP_CODE_CONFIRMATION(getNavRoute(NavigationType.SECONDARY, "signUpCodeConfirmation")),
    NAME_PASSWORD(getNavRoute(NavigationType.SECONDARY, "nameAndPassword")),
    PICK_USERNAME(getNavRoute(NavigationType.SECONDARY, "changeUsername")),
    FORGOT_PASSWORD(getNavRoute(NavigationType.SECONDARY, "forgotPassword")),
    FORGOT_PASSWORD_CODE_CONFIRMATION(
        getNavRoute(
            NavigationType.SECONDARY,
            "forgotPasswordCodeConfirmation"
        )
    ),
    NEW_PASSWORD(getNavRoute(NavigationType.SECONDARY, "newPassword"))
}

fun NavGraphBuilder.loginNavGraph(threadNavController: ThreadNavController) {
    composable(LoginDestination.LOGIN.route) {
        LoginScreen(threadNavController = threadNavController)
    }
    composable(LoginDestination.SIGN_UP.route) {
        EnterEmailScreen(threadNavController = threadNavController, title = "Use the Email to Sign Up") { isExists, existAlert, notExistAlert ->
            if (isExists) {
                existAlert()
            } else {
                threadNavController.navigate(LoginDestination.SIGN_UP_CODE_CONFIRMATION.route)
                SignUpViewModelProvider.getInstance().getConfirmationCode()
            }
        }
    }
    composable(LoginDestination.SIGN_UP_CODE_CONFIRMATION.route) {
        CodeConfirmationScreen(threadNavController = threadNavController) {
            threadNavController.navigate(LoginDestination.NAME_PASSWORD.route)
        }
    }
    composable(LoginDestination.NAME_PASSWORD.route) {
        NameAndPasswordScreen(threadNavController = threadNavController)
    }
    composable(LoginDestination.PICK_USERNAME.route) {
        PickUsername(threadNavController = threadNavController)
    }
    composable(LoginDestination.FORGOT_PASSWORD.route) {
        EnterEmailScreen(threadNavController = threadNavController, title = "Enter your email") { isExists, existAlert, notExistAlert ->
            if (isExists) {
                threadNavController.navigate(LoginDestination.FORGOT_PASSWORD_CODE_CONFIRMATION.route)
                SignUpViewModelProvider.getInstance().getConfirmationCode()
            } else {
                notExistAlert()
            }
        }
    }
    composable(LoginDestination.FORGOT_PASSWORD_CODE_CONFIRMATION.route) {
        CodeConfirmationScreen(threadNavController = threadNavController) {
            threadNavController.navigate(LoginDestination.NEW_PASSWORD.route)
        }
    }
    composable(LoginDestination.NEW_PASSWORD.route) {
        NewPasswordScreen(threadNavController = threadNavController)
    }
}
