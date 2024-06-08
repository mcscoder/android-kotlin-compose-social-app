package com.example.thread.ui.navigation.thread

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thread.data.model.thread.ThreadType
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.primary.newthread.ReplyToThreadScreen
import com.example.thread.ui.screen.secondary.threaddetails.ThreadDetailsScreen

enum class ThreadDestination(val route: String) {
    DEFAULT("thread"),
    THREAD_DETAILS(getNavRoute(NavigationType.SECONDARY, DEFAULT.route, "1")),
    REPLY_TO_THREAD(getNavRoute(NavigationType.SECONDARY, DEFAULT.route, "2")),
}

object ThreadDetailsData {
    private val threadsDataList: MutableList<ThreadsData?> = mutableListOf()

    fun setThreadsData(threadsData: ThreadsData): Int {
        threadsDataList.add(threadsData)
        return threadsDataList.size - 1
    }

    fun getThreadsData(threadsDataIndex: Int): ThreadsData? {
        return threadsDataList[threadsDataIndex]
    }

    fun removeThreadsDataAt(threadsDataIndex: Int) {
        threadsDataList[threadsDataIndex] = null
    }
}

fun NavGraphBuilder.threadNavGraph(threadNavController: ThreadNavController) {
    composable(
        route = "${ThreadDestination.THREAD_DETAILS.route}/{threadsDataIndex}/{threadIndex}",
        arguments = listOf(
            navArgument("threadsDataIndex") { type = NavType.IntType },
            navArgument("threadIndex") { type = NavType.IntType }),
    ) { entry ->
        val threadsDataIndex = entry.arguments?.getInt(("threadsDataIndex"))
        val threadIndex = entry.arguments?.getInt(("threadIndex"))
        if (threadsDataIndex != null && threadIndex != null) {
            ThreadDetailsScreen(threadNavController, threadsDataIndex, threadIndex)
        }
    }
    composable(
        route = "${ThreadDestination.REPLY_TO_THREAD.route}/{threadsDataIndex}/{threadIndex}/{threadType}",
        arguments = listOf(
            navArgument("threadsDataIndex") { type = NavType.IntType },
            navArgument("threadIndex") { type = NavType.IntType },
            navArgument("threadType") { type = NavType.IntType },
        ),
    ) {entry ->
        val threadsDataIndex = entry.arguments?.getInt(("threadsDataIndex"))
        val threadIndex = entry.arguments?.getInt(("threadIndex"))
        val threadType = entry.arguments?.getInt(("threadType"))
        if (threadsDataIndex != null && threadIndex != null && threadType != null) {
            ReplyToThreadScreen(threadNavController, threadsDataIndex, threadIndex, threadType)
        }
    }
}
