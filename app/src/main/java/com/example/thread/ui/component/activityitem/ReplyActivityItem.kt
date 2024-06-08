package com.example.thread.ui.component.activityitem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.ui.component.feed.FeedCardContent

@Composable
fun ReplyActivityItem(
    modifier: Modifier = Modifier,
    threadData: ThreadResponse,
    onClick: () -> Unit = {},
) {
    ActivityItem(
        modifier = modifier,
        avatarURL = threadData.user.user.imageUrl,
        title = threadData.user.user.username,
        description = {
            FeedCardContent(threadData)
        },
        onClick = onClick,
        timeStamp = threadData.content.dateTime.createdAt,
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ReplyActivityItemPreview() {
    // Column {
    //     ReplyActivityItem(threadData = feedCardSampleData)
    //     ReplyActivityItem(threadData = feedCardSampleData)
    //     ReplyActivityItem(threadData = feedCardSampleData)
    // }
}
