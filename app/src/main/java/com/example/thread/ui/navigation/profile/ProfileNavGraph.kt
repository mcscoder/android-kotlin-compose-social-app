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
    ),
    THREAD_DETAILS(
        getNavRoute(
            NavigationType.SECONDARY,
            DEFAULT.route,
            "1"
        )
    ),
    REPLY_THREAD(getNavRoute(NavigationType.SECONDARY, DEFAULT.route, "2")),
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

    // 2. Thread Details Screen
    // composable(
    //     route = "${ProfileDestination.THREAD_DETAILS.route}/{threadIndex}",
    //     arguments = listOf(navArgument("threadIndex") { type = NavType.IntType }),
    //     enterTransition = { rightToLeftSlideInAnimation() },
    //     exitTransition = { leftToRightSlideOutAnimation() }
    // ) { entry ->
    //     val threadIndex = entry.arguments?.getInt(("threadIndex"))
    //     if (threadIndex != null) {
    //         ProfileThreadDetailsScreen(threadNavController, threadIndex)
    //     }
    // }

    // 3. Reply Thread Screen
    // composable(
    //     route = "${ProfileDestination.REPLY_THREAD.route}/{threadIndex}",
    //     arguments = listOf(navArgument("threadIndex") { type = NavType.IntType }),
    //     enterTransition = { bottomToTopSlideInAnimation() },
    //     exitTransition = { topToBottomSlideOutAnimation() }
    // ) { entry ->
    //     val threadIndex = entry.arguments?.getInt("threadIndex")
    //     if (threadIndex != null) {
    //         ProfileReplyThreadScreen(threadNavController, threadIndex)
    //     }
    // }
}
