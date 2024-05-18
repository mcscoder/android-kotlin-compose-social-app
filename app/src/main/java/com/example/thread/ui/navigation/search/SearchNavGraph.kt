package com.example.thread.ui.navigation.search

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.navigation.profile.profileNavGraph
import com.example.thread.ui.screen.primary.search.SearchScreen

enum class SearchDestination(val route: String) {
    START_DESTINATION(getNavRoute(NavigationType.PRIMARY, ThreadNavigationItem.SEARCH.route))
}

fun NavGraphBuilder.searchNavGraph(
    threadNavController: ThreadNavController
) {
    navigation(
        route = ThreadNavigationItem.SEARCH.route,
        startDestination = SearchDestination.START_DESTINATION.route
    ) {
        composable(SearchDestination.START_DESTINATION.route) {
            SearchScreen(threadNavController)
        }
        profileNavGraph(threadNavController)
    }
}
