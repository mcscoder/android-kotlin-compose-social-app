package com.example.thread.ui.navigation.newthread

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.thread.ui.constant.bottomToTopSlideInAnimation
import com.example.thread.ui.constant.topToBottomSlideOutAnimation
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.primary.newthread.NewThreadScreen

enum class NewThreadDestination(val route: String) {
    START_DESTINATION(getNavRoute(NavigationType.SECONDARY, ThreadNavigationItem.NEW_THREAD.route))
}

fun NavGraphBuilder.newThreadNavGraph(threadNavController: ThreadNavController) {
    navigation(
        route = ThreadNavigationItem.NEW_THREAD.route,
        startDestination = NewThreadDestination.START_DESTINATION.route
    ) {
        composable(
            NewThreadDestination.START_DESTINATION.route,
            enterTransition = { bottomToTopSlideInAnimation() },
            exitTransition = { topToBottomSlideOutAnimation() }
        ) {
            NewThreadScreen(threadNavController)
        }
    }
}
