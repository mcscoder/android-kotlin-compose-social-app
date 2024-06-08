package com.example.thread.ui.navigation.myprofile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.thread.ui.constant.leftToRightSlideOutAnimation
import com.example.thread.ui.constant.rightToLeftSlideInAnimation
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.GlobalViewModelProvider
import com.example.thread.ui.screen.primary.profile.ProfileScreen
import com.example.thread.ui.screen.secondary.setting.SettingScreen

enum class MyProfileDestination(val route: String) {
    START_DESTINATION(
        getNavRoute(NavigationType.PRIMARY, ThreadNavigationItem.MY_PROFILE.route)
    ),
    SETTING(getNavRoute(NavigationType.SECONDARY, ThreadNavigationItem.MY_PROFILE.route, "1"))
}

fun NavGraphBuilder.myProfileNavGraph(threadNavController: ThreadNavController) {
    navigation(
        route = ThreadNavigationItem.MY_PROFILE.route,
        startDestination = MyProfileDestination.START_DESTINATION.route
    ) {
        // 1. Profile Screen
        composable(MyProfileDestination.START_DESTINATION.route) {
            ProfileScreen(
                threadNavController,
                GlobalViewModelProvider.getCurrentUserId()
            )
        }

        // 2. Setting Screen [Secondary]
        composable(
            route = MyProfileDestination.SETTING.route,
            enterTransition = { rightToLeftSlideInAnimation() },
            exitTransition = { leftToRightSlideOutAnimation() }
        ) {
            SettingScreen(threadNavController = threadNavController)
        }
    }
}
