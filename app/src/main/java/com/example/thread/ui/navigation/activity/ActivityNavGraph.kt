package com.example.thread.ui.navigation.activity

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.primary.activity.ActivityScreen

enum class ActivityDestination(val route: String) {
    START_DESTINATION(getNavRoute(NavigationType.PRIMARY, ThreadNavigationItem.ACTIVITY.route))
}

fun NavGraphBuilder.activityNavGraph(threadNavController: ThreadNavController) {
    navigation(
        route = ThreadNavigationItem.ACTIVITY.route,
        startDestination = ActivityDestination.START_DESTINATION.route
    ) {
        composable(ActivityDestination.START_DESTINATION.route) {
            ActivityScreen(threadNavController)
        }
    }
}
