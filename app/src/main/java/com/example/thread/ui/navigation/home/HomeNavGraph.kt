package com.example.thread.ui.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.screen.primary.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    threadNavController: ThreadNavController,
) {
    composable(ThreadNavigationItem.HOME.route) {
        HomeScreen(threadNavController)
    }
}
