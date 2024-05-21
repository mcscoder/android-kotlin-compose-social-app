package com.example.thread.ui.navigation.thread

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.thread.data.viewmodel.threaddata.MainThreads
import com.example.thread.data.viewmodel.threaddata.ThreadReplies
import com.example.thread.ui.navigation.NavigationType
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.getNavRoute
import com.example.thread.ui.screen.primary.newthread.ReplyThreadReplyingScreen
import com.example.thread.ui.screen.primary.newthread.ReplyThreadScreen
import com.example.thread.ui.screen.secondary.threaddetails.MainThreadDetailsScreen
import com.example.thread.ui.screen.secondary.threaddetails.ReplyDetailsScreen

object ThreadDetailsData {
    var threadsData: MainThreads? = null
        private set
    var threadIndex: Int? = null
        private set

    var threadRepliesData: ThreadReplies? = null
        private set
    var threadReplyIndex: Int? = null
        private set

    fun setThreadsData(newThreadsData: MainThreads, newThreadIndex: Int) {
        threadsData = newThreadsData
        threadIndex = newThreadIndex
    }

    fun setThreadRepliesData(newThreadRepliesData: ThreadReplies, newThreadReplyIndex: Int) {
        threadRepliesData = newThreadRepliesData
        threadReplyIndex = newThreadReplyIndex
    }
}

enum class ThreadDestination(val route: String) {
    DEFAULT("thread"),
    THREAD_DETAILS(getNavRoute(NavigationType.SECONDARY, DEFAULT.route, "1")),
    REPLY_THREAD(getNavRoute(NavigationType.SECONDARY, DEFAULT.route, "2")),
    REPLY_THREAD_REPLYING(getNavRoute(NavigationType.SECONDARY, DEFAULT.route, "3")),
    THREAD_REPLY_DETAILS(getNavRoute(NavigationType.SECONDARY, DEFAULT.route, "4"))
}

fun NavGraphBuilder.threadNavGraph(threadNavController: ThreadNavController) {
    composable(ThreadDestination.THREAD_DETAILS.route) {
        MainThreadDetailsScreen(threadNavController)
    }
    composable(ThreadDestination.REPLY_THREAD.route) {
        ReplyThreadScreen(threadNavController)
    }
    composable(ThreadDestination.REPLY_THREAD_REPLYING.route) {
        ReplyThreadReplyingScreen(threadNavController)
    }
    composable(ThreadDestination.THREAD_REPLY_DETAILS.route) {
        ReplyDetailsScreen(threadNavController)
    }
}
