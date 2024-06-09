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
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.ui.component.activityitem.ActivityFollowItem
import com.example.thread.ui.component.activityitem.ActivityReplyItem
import com.example.thread.ui.component.layout.TabRowLayout
import com.example.thread.ui.component.text.TextHeadLine
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.CoroutineScope
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
                    viewModel.replies.getActivityReplies()
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
                    viewModel.replies.getActivityReplies()
                } else {
                    itemsIndexed(replies) { index, reply ->
                        ActivityReplyItem(threadData = reply, onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                val threadsData = ThreadsData()
                                threadsData.getThreadById(reply.content.mainId!!)
                                launch(Dispatchers.Main) {
                                    threadNavController.navigateToThreadDetails(threadsData, 0)
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
                        ActivityFollowItem(
                            activityFollow = followActivity,
                            onClick = {
                                threadNavController.navigateToUserProfile(followActivity.user.user.userId)
                            },
                            onActionClick = {
                                viewModel.follows.onFollowUser(followActivity.user.user.userId) {
                                    viewModel.follows.retrieveUserFollowers(GlobalViewModelProvider.getCurrentUserId())
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
