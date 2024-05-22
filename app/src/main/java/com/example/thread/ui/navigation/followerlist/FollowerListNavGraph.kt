package com.example.thread.ui.navigation.followerlist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.secondary.followerlist.FollowerListScreen

private const val defaultDestination: String = "followerList"

enum class FollowerListDestination(val route: String) {
    FOLLOWER_LIST(getNavRoute(NavigationType.PRIMARY, defaultDestination))
}

fun NavGraphBuilder.followerListNavGraph(threadNavController: ThreadNavController) {
    composable(
        route = "${FollowerListDestination.FOLLOWER_LIST.route}/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.IntType })
    ) { entry ->
        val userId = entry.arguments?.getInt(("userId"))
        if (userId != null) {
            FollowerListScreen(threadNavController = threadNavController, userId = userId)
        }
    }
}
