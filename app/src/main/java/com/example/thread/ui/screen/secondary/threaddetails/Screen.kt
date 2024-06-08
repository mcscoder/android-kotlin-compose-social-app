package com.example.thread.ui.screen.secondary.threaddetails

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.thread.data.model.thread.ThreadType
import com.example.thread.ui.component.button.IconClickable
import com.example.thread.ui.component.layout.LazyThreadDetailsLayout
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.thread.ThreadDetailsData

@Composable
fun ThreadDetailsScreen(
    threadNavController: ThreadNavController,
    threadsDataIndex: Int,
    threadIndex: Int,
) {
    val viewModel = remember {
        val threadsData = ThreadDetailsData.getThreadsData(threadsDataIndex)!!
        ThreadDetailsViewModel(threadsData, threadIndex)
    }

    val mainThread = viewModel.threadsData.data.collectAsState().value[threadIndex]
    val replies = viewModel.repliesData.data.collectAsState().value

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
                },
                onNavigateUp = {
                    ThreadDetailsData.removeThreadsDataAt(threadsDataIndex)
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            LazyThreadDetailsLayout(
                threadNavController = threadNavController,
                onFavoriteClick = { isFavorited ->
                    viewModel.threadsData.favoriteThread(isFavorited, threadIndex)
                },
                onFavoriteReplyClick = { isFavorited, replyIndex ->
                    viewModel.repliesData.favoriteThread(isFavorited, replyIndex)
                },
                onCommentClick = {
                    threadNavController.navigateToReplyToThread(
                        viewModel.threadsData,
                        threadIndex
                    )
                },
                onReplyClick = { replyIndex ->
                    threadNavController.navigateToReplyToThread(
                        viewModel.repliesData,
                        replyIndex,
                    )
                },
                onReplyCardClick = { replyIndex ->
                    if (mainThread.content.type == ThreadType.POST.ordinal) {
                        threadNavController.navigateToThreadDetails(
                            viewModel.repliesData,
                            replyIndex,
                        )
                    }
                },
                ableToReply = mainThread.content.type == ThreadType.POST.ordinal,
                thread = mainThread,
                threadReplies = replies
            )
        }
    }
}
