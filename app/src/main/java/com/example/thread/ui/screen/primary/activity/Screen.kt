package com.example.thread.ui.screen.primary.activity

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thread.data.model.activity.ReplyType
import com.example.thread.data.viewmodel.threaddata.MainThreads
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.ui.component.activityitem.ReplyActivityItem
import com.example.thread.ui.component.feed.feedCardSampleData
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
        ActivityViewModel()
    }
    val replies = viewModel.replies.data.collectAsState().value

    TabRowLayout(
        modifier = modifier,
        title = {
            TextHeadLine(
                text = "Activity",
                fontSize = 32.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        },
        tabTitles = listOf("Replies", "Follows"),
        onRefresh = { currentPage ->
            when (currentPage) {
                ActivityScreenIndex.REPLIES.ordinal -> {
                    viewModel.replies.retrieveReplyActivities()
                }
            }
        }
    ) { currentPage ->
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
                                    // Log.d("replyActivity", "")
                                    val threadsData: MainThreads = ThreadsData()
                                    threadsData.retrieveThreadById(
                                        reply.reply.content.mainThreadId!!
                                    ) {
                                        launch(Dispatchers.Main) {
                                            ThreadDetailsData.setThreadsData(threadsData, 0)
                                            threadNavController.navigate(ThreadDestination.THREAD_DETAILS.route)
                                        }
                                    }
                                }

                                ReplyType.REPLYING_REPLY.ordinal -> {
                                    // Handle later
                                }
                            }
                        })
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
