package com.example.thread.ui.screen.secondary.threaddetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.thread.data.model.thread.Thread
import com.example.thread.data.viewmodel.ThreadReplies
import com.example.thread.data.viewmodel.ThreadReplyingReplies
import com.example.thread.data.viewmodel.ThreadsData
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.button.IconClickable
import com.example.thread.ui.component.layout.LazyThreadDetailsLayout
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.thread.ThreadDestination
import com.example.thread.ui.navigation.thread.ThreadDetailsData

@Composable
fun ThreadPostDetailsScreen(
    threadNavController: ThreadNavController,
    onFavoriteClick: (isFavorite: Boolean) -> Unit = {},
    onFavoriteReplyClick: (isFavorite: Boolean, threadReplyIndex: Int) -> Unit,
    onReplyClick: () -> Unit = {},
    onReplyReplyingClick: (threadReplyingIndex: Int) -> Unit = {},
    onReplyCardClick: (index: Int) -> Unit,
    ableToReply: Boolean = true,
    thread: Thread,
    replies: List<Thread>,
) {

    ThreadScaffold(
        topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                title = "Thread",
                actions = {
                    IconClickable(
                        imageVector = Icons.Rounded.NotificationsNone,
                        onClick = {}
                    )
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            LazyThreadDetailsLayout(
                threadNavController = threadNavController,
                onFavoriteClick = onFavoriteClick,
                onFavoriteReplyClick = onFavoriteReplyClick,
                onReplyClick = onReplyClick,
                onReplyReplyingClick = onReplyReplyingClick,
                onReplyCardClick = onReplyCardClick,
                ableToReply = ableToReply,
                thread = thread,
                threadReplies = replies
            )
        }
    }
}

// 1. Main Thread Details
@Composable
fun MainThreadDetailsScreen(threadNavController: ThreadNavController) {
    val threadsData = ThreadDetailsData.threadsData
    val threadIndex = ThreadDetailsData.threadIndex

    if (threadsData != null && threadIndex != null) {
        val thread = threadsData.data.collectAsState().value[threadIndex]
        val threadRepliesData = remember {
            val data: ThreadReplies = ThreadsData()
            data.retrieveThreadRepliesData(thread.content.id)
            data
        }
        val replies = threadRepliesData.data.collectAsState().value
        ThreadPostDetailsScreen(
            threadNavController = threadNavController,
            thread = thread,
            replies = replies,
            onFavoriteClick = { isFavorite ->
                threadsData.favoritePost(isFavorite, threadIndex)
            },
            onFavoriteReplyClick = { isFavorite, threadReplyIndex ->
                threadRepliesData.favoriteThreadReply(isFavorite, threadReplyIndex)
            },
            onReplyClick = {
                threadNavController.navigate(ThreadDestination.REPLY_THREAD.route)
            },
            onReplyReplyingClick = { index ->
                ThreadDetailsData.setThreadRepliesData(threadRepliesData, index)
                threadNavController.navigate(ThreadDestination.REPLY_THREAD_REPLYING.route)
            },
            onReplyCardClick = { index ->
                ThreadDetailsData.setThreadRepliesData(threadRepliesData, index)
                threadNavController.navigate(ThreadDestination.THREAD_REPLY_DETAILS.route)
            }
        )
    }
}

// 2. Reply Details
@Composable
fun ReplyDetailsScreen(threadNavController: ThreadNavController) {
    val threadRepliesData = ThreadDetailsData.threadRepliesData
    val threadReplyIndex = ThreadDetailsData.threadReplyIndex

    if (threadRepliesData != null && threadReplyIndex != null) {
        val reply = threadRepliesData.data.collectAsState().value[threadReplyIndex]
        val replyingRepliesData = remember {
            val data: ThreadReplyingReplies = ThreadsData()
            data.retrieveThreadReplyingReplies(reply.content.id)
            data
        }
        val replyingReplies = replyingRepliesData.data.collectAsState().value
        ThreadPostDetailsScreen(
            threadNavController = threadNavController,
            thread = reply,
            replies = replyingReplies,
            onFavoriteClick = { isFavorite ->
                threadRepliesData.favoriteThreadReply(isFavorite, threadReplyIndex)
            },
            onFavoriteReplyClick = { isFavorite, threadReplyingReplyIndex ->
                replyingRepliesData.favoriteThreadReplyingReply(
                    isFavorite,
                    threadReplyingReplyIndex
                )
            },
            onReplyClick = {
                ThreadDetailsData.setThreadRepliesData(threadRepliesData, threadReplyIndex)
                threadNavController.navigate(ThreadDestination.REPLY_THREAD_REPLYING.route)
            },
            onReplyReplyingClick = {},
            onReplyCardClick = {},
            ableToReply = false
        )
    }
}

//
// @Preview(showBackground = true, showSystemUi = true)
// @Composable
// private fun ThreadPostDetailsScreenPreview() {
//     ThreadTheme(darkTheme = false) {
//         ThreadPostDetailsScreen(
//             ThreadNavController(rememberNavController()),
//             globalViewModel = viewModel(),
//             threadId = 1
//         )
//     }
// }
