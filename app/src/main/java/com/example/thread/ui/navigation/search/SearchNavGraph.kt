package com.example.thread.ui.navigation.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.primary.search.SearchResultsScreen
import com.example.thread.ui.screen.primary.search.SearchScreen

enum class SearchDestination(val route: String) {
    RESULT_DESTINATION(
        getNavRoute(
            NavigationType.SECONDARY,
            ThreadNavigationItem.SEARCH.route,
            "1"
        )
    )
}

fun NavGraphBuilder.searchNavGraph(
    threadNavController: ThreadNavController,
) {
    composable(ThreadNavigationItem.SEARCH.route) {
        SearchScreen(threadNavController)
    }
    composable(
        route = "${SearchDestination.RESULT_DESTINATION.route}/{searchText}",
        arguments = listOf(navArgument("searchText") { type = NavType.StringType })
    ) { entry ->
        val searchText = entry.arguments?.getString("searchText")
        if (searchText != null) {
            SearchResultsScreen(
                threadNavController = threadNavController,
                searchText = searchText
            )
        }
    }
}
