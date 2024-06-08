package com.example.thread.ui.screen.primary.activity

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thread.data.model.activity.ReplyType
import com.example.thread.data.viewmodel.threaddata.MainThreads
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.ui.component.activityitem.FollowActivityItem
import com.example.thread.ui.component.activityitem.ReplyActivityItem
import com.example.thread.ui.component.layout.TabRowLayout
import com.example.thread.ui.component.text.TextHeadLine
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.thread.ThreadDestination
import com.example.thread.ui.navigation.thread.ThreadDetailsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ActivityScreen(threadNavController: ThreadNavController, modifier: Modifier = Modifier) {
    val viewModel = remember {
        ActivityViewModelProvider.getInstance()
    }
    val replies = viewModel.replies.data.collectAsState().value
    val follows = viewModel.follows.data.collectAsState().value

    TabRowLayout(
        modifier = modifier,
        title = {
            TextHeadLine(
                text = "Activity",
                fontSize = 32.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        },
        initialPage = ActivityViewModelProvider.currentPageIndex,
        tabTitles = listOf("Replies", "Follows"),
        onRefresh = { currentPage ->
            when (currentPage) {
                ActivityScreenIndex.REPLIES.ordinal -> {
                    viewModel.replies.retrieveReplyActivities()
                }

                ActivityScreenIndex.FOLLOWS.ordinal -> {
                    viewModel.retrieveFollowersData()
                }
            }
        }
    ) { currentPage ->
        ActivityViewModelProvider.currentPageIndex = currentPage
        when (currentPage) {
            ActivityScreenIndex.REPLIES.ordinal -> {
                if (replies.isEmpty()) {
                    viewModel.replies.retrieveReplyActivities()
                } else {
                    itemsIndexed(replies) { index, reply ->
                        ReplyActivityItem(threadData = reply.reply, onClick = {
                            when (reply.type) {
                                ReplyType.REPLY.ordinal -> {
                                    // Navigate to main Thread details
                                    // val threadsData = ThreadsData()
                                    // threadsData.retrieveThreadById(
                                    //     reply.reply.content.mainThreadId!!
                                    // ) {
                                    //     launch(Dispatchers.Main) {
                                    //         ThreadDetailsData.setThreadsData(threadsData, 0)
                                    //         threadNavController.navigate(ThreadDestination.THREAD_DETAILS.route)
                                    //     }
                                    // }
                                }

                                ReplyType.REPLYING_REPLY.ordinal -> {
                                    // Handle later
                                }
                            }
                        })
                    }
                }
            }

            ActivityScreenIndex.FOLLOWS.ordinal -> {
                if (follows.isEmpty()) {
                    viewModel.retrieveFollowersData()
                } else {
                    itemsIndexed(follows) { index, followActivity ->
                        FollowActivityItem(
                            followActivity = followActivity,
                            onClick = {
                                threadNavController.navigateToUserProfile(followActivity.user.userId)
                            },
                            onActionClick = {
                                viewModel.follows.onFollowUser(followActivity.user.userId) { currentUserId ->
                                    viewModel.follows.retrieveFollowersData(currentUserId)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

enum class ActivityScreenIndex {
    REPLIES,
    FOLLOWS
}
