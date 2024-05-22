package com.example.thread.ui.navigation.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thread.ui.constant.leftToRightSlideOutAnimation
import com.example.thread.ui.constant.rightToLeftSlideInAnimation
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.primary.profile.ProfileScreen

enum class ProfileDestination(val route: String) {
    DEFAULT("profile"),
    PROFILE(
        getNavRoute(NavigationType.PRIMARY, DEFAULT.route)
    )
}

fun NavGraphBuilder.profileNavGraph(threadNavController: ThreadNavController) {
    // 1. Profile Screen
    composable(
        route = "${ProfileDestination.PROFILE.route}/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.IntType }),
        enterTransition = { rightToLeftSlideInAnimation() },
        exitTransition = { leftToRightSlideOutAnimation() }
    ) { entry ->
        val userId = entry.arguments?.getInt(("userId"))
        if (userId != null) {
            ProfileScreen(threadNavController, userId)
        }
    }
}
