package com.example.thread.ui.component.layout

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.feed.FeedCardDetails
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun LazyThreadDetailsLayout(
    modifier: Modifier = Modifier,
    threadNavController: ThreadNavController,
    onFavoriteClick: (isFavorite: Boolean) -> Unit,
    onFavoriteReplyClick: (isFavorited: Boolean, replyIndex: Int) -> Unit,
    onCommentClick: () -> Unit,
    onReplyClick: (threadReplyingIndex: Int) -> Unit,
    onReplyCardClick: (index: Int) -> Unit,
    ableToReply: Boolean = true,
    thread: ThreadResponse,
    threadReplies: List<ThreadResponse>,
) {
    LazyColumn(modifier = modifier) {
        lazyThreadDetailsCard(
            threadNavController,
            onFavoriteClick,
            onFavoriteReplyClick,
            onCommentClick,
            onReplyClick,
            onReplyCardClick,
            ableToReply,
            thread,
            threadReplies,
        )
    }
}

fun LazyListScope.lazyThreadDetailsCard(
    threadNavController: ThreadNavController,
    onFavoriteClick: (isFavorite: Boolean) -> Unit,
    onFavoriteReplyClick: (isFavorite: Boolean, threadReplyIndex: Int) -> Unit,
    onReplyClick: () -> Unit,
    onReplyReplyingClick: (threadReplyingIndex: Int) -> Unit,
    onReplyCardClick: (index: Int) -> Unit,
    ableToReply: Boolean = true,
    thread: ThreadResponse,
    threadReplies: List<ThreadResponse>,
    showBottomDivider: Boolean = false,
) {
    item {
        FeedCardDetails(
            threadNavController = threadNavController,
            onFavoriteClick = onFavoriteClick,
            onReplyClick = onReplyClick,
            thread = thread,
            showHorizontalDivider = !showBottomDivider
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
            ableToReply = ableToReply,
            showHorizontalDivider = !showBottomDivider
        )
    }
    if (showBottomDivider) {
        item {
            ThreadHorizontalDivider()
        }
    }
}
