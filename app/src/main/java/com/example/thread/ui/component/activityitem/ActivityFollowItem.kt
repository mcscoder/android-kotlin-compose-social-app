package com.example.thread.ui.component.activityitem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.thread.data.model.user.ActivityFollowResponse
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.button.ButtonVariant
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.screen.GlobalViewModelProvider

@Composable
fun ActivityFollowItem(
    modifier: Modifier = Modifier,
    activityFollow: ActivityFollowResponse,
    onClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    showTimeStamp: Boolean = true,
) {
    ActivityItem(
        modifier = modifier,
        avatarURL = activityFollow.user.user.imageUrl,
        title = activityFollow.user.user.username,
        timeStamp = activityFollow.dateTime.createdAt,
        description = {
            TextBody(
                text = "${activityFollow.user.user.firstName} ${activityFollow.user.user.lastName}",
                color = Color.Gray
            )
        },
        onClick = onClick,
        action = {
            if (activityFollow.user.user.userId != GlobalViewModelProvider.getCurrentUserId()) {
                Button(
                    onClick = onActionClick,
                    buttonVariant = ButtonVariant.OUTLINED,
                    rounded = false,
                    disable = true
                ) {
                    TextBody(
                        text = if (activityFollow.user.overview.follow.isFollowing) "Following" else "Follow",
                        bold = true,
                        color = if (activityFollow.user.overview.follow.isFollowing) Color.Gray else Color.Black
                    )
                }
            }
        },
        showTimeStamp = showTimeStamp
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ActivityFollowItemPreview() {
    Column {
        // FollowActivityItem(
        //     timeStamp = 1716181099999,
        //     // user =
        // )
    }
}
