package com.example.thread.ui.component.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.thread.data.model.thread.Thread
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.feed.FeedCardDetails
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun LazyThreadDetailsLayout(
    modifier: Modifier = Modifier,
    threadNavController: ThreadNavController,
    onFavoriteClick: (isFavorite: Boolean) -> Unit,
    onFavoriteReplyClick: (isFavorite: Boolean, threadReplyIndex: Int) -> Unit,
    onReplyClick: () -> Unit,
    onReplyReplyingClick: (threadReplyingIndex: Int) -> Unit,
    onReplyCardClick: (index: Int) -> Unit,
    ableToReply: Boolean = true,
    thread: Thread,
    threadReplies: List<Thread>,
) {
    LazyColumn(modifier = modifier) {
        item {
            FeedCardDetails(
                threadNavController = threadNavController,
                onFavoriteClick = onFavoriteClick,
                onReplyClick = onReplyClick,
                thread = thread
            )
        }
        itemsIndexed(threadReplies) { index, threadReply ->
            FeedCard(
                threadNavController = threadNavController,
                threadData = threadReply,
                onFavoriteClick = { isFavorite ->
                    onFavoriteReplyClick(isFavorite, index)
                },
                onReplyClick = { onReplyReplyingClick(index) },
                onFeedCardClick = {
                    onReplyCardClick(index)
                },
                showVerticalDivider = true,
                ableToReply = ableToReply
            )
        }
    }
}

@Composable
fun ThreadDetailsLayout(
    threadNavController: ThreadNavController,
    onFavoriteClick: (isFavorite: Boolean) -> Unit,
    onFavoriteReplyClick: (isFavorite: Boolean, threadReplyIndex: Int) -> Unit,
    onReplyClick: () -> Unit,
    onReplyReplyingClick: (threadReplyingIndex: Int) -> Unit,
    thread: Thread,
    threadReplies: List<Thread>,
    modifier: Modifier = Modifier,
    showBottomDivider: Boolean = false,
) {
    Column(modifier = modifier) {
        FeedCardDetails(
            threadNavController = threadNavController,
            onFavoriteClick = onFavoriteClick,
            onReplyClick = onReplyClick,
            thread = thread,
            showHorizontalDivider = !showBottomDivider
        )
        threadReplies.forEachIndexed { index, threadReply ->
            FeedCard(
                threadNavController = threadNavController,
                threadData = threadReply,
                onFavoriteClick = { onFavoriteReplyClick(it, index) },
                onReplyClick = { onReplyReplyingClick(index) },
                showVerticalDivider = true,
                showHorizontalDivider = !showBottomDivider
            )
        }
        if (showBottomDivider) {
            ThreadHorizontalDivider()
        }
    }
}
