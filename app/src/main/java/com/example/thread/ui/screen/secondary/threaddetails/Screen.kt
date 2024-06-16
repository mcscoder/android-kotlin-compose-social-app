package com.example.thread.ui.screen.secondary.threaddetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    threadDetailsIndex: Int,
) {
    val viewModel = remember {
        ThreadDetailsData.getThreadDetails(threadDetailsIndex)!!
    }

    val mainThread = viewModel.threadsData.data.collectAsState().value[viewModel.threadIndex]
    val replies = viewModel.repliesData.data.collectAsState().value

    LaunchedEffect(null) {
        viewModel.getReplies()
    }

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
                    ThreadDetailsData.removeThreadDetailsAt(threadDetailsIndex)
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            LazyThreadDetailsLayout(
                threadNavController = threadNavController,
                onFavoriteToPostClick = { isFavorited ->
                    viewModel.threadsData.favoriteThread(isFavorited, viewModel.threadIndex)
                },
                onFavoriteToReplyClick = { isFavorited, replyIndex ->
                    viewModel.repliesData.favoriteThread(isFavorited, replyIndex)
                },
                onReplyToPostClick = {
                    threadNavController.navigateToReplyToThread(
                        viewModel.threadsData,
                        viewModel.threadIndex
                    )
                },
                onReplyToReplyClick = { replyIndex ->
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
                onPostCardClick = {},
                ableToReplyToReply = mainThread.content.type == ThreadType.POST.ordinal,
                thread = mainThread,
                threadReplies = replies
            )
        }
    }
}
