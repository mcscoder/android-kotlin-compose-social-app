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
    onFavoriteToPostClick: (isFavorited: Boolean) -> Unit,
    onReplyToPostClick: () -> Unit,
    onPostCardClick: () -> Unit,
    ableToClickPostCard: Boolean = false,

    onFavoriteToReplyClick: (isFavorite: Boolean, replyIndex: Int) -> Unit,
    onReplyToReplyClick: (replyIndex: Int) -> Unit,
    onReplyCardClick: (index: Int) -> Unit,
    ableToReplyToReply: Boolean = true,

    thread: ThreadResponse,
    threadReplies: List<ThreadResponse>
) {
    LazyColumn(modifier = modifier) {
        lazyThreadDetailsCard(
            threadNavController,
            onFavoriteToPostClick,
            onReplyToPostClick,
            onPostCardClick,
            ableToClickPostCard,
            onFavoriteToReplyClick,
            onReplyToReplyClick,
            onReplyCardClick,
            ableToReplyToReply,
            thread,
            threadReplies,
        )
    }
}

fun LazyListScope.lazyThreadDetailsCard(
    threadNavController: ThreadNavController,
    onFavoriteToPostClick: (isFavorited: Boolean) -> Unit,
    onReplyToPostClick: () -> Unit,
    onPostCardClick: () -> Unit,
    ableToClickPostCard: Boolean = false,

    onFavoriteToReplyClick: (isFavorite: Boolean, replyIndex: Int) -> Unit,
    onReplyToReplyClick: (replyIndex: Int) -> Unit,
    onReplyCardClick: (index: Int) -> Unit,
    ableToReplyToReply: Boolean = true,

    thread: ThreadResponse,
    threadReplies: List<ThreadResponse>,
    showBottomDivider: Boolean = false,
) {
    item {
        FeedCardDetails(
            threadNavController = threadNavController,
            onFavoriteClick = onFavoriteToPostClick,
            onFeedCardClick = onPostCardClick,
            onReplyClick = onReplyToPostClick,
            thread = thread,
            showHorizontalDivider = !showBottomDivider,
            clickable = ableToClickPostCard
        )
    }
    itemsIndexed(threadReplies) { index, threadReply ->
        FeedCard(
            threadNavController = threadNavController,
            threadData = threadReply,
            onFavoriteClick = { isFavorite ->
                onFavoriteToReplyClick(isFavorite, index)
            },
            onReplyClick = { onReplyToReplyClick(index) },
            onFeedCardClick = {
                onReplyCardClick(index)
            },
            showVerticalDivider = true,
            ableToReply = ableToReplyToReply,
            showHorizontalDivider = !showBottomDivider
        )
    }
    if (showBottomDivider) {
        item {
            ThreadHorizontalDivider()
        }
    }
}
