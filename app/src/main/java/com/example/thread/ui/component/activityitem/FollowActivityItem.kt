package com.example.thread.ui.component.activityitem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.thread.data.model.user.FollowStatus
import com.example.thread.data.model.user.OtherUser
import com.example.thread.data.model.user.OtherUserStatus
import com.example.thread.data.model.user.User
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.button.ButtonVariant
import com.example.thread.ui.component.text.TextBody

@Composable
fun FollowActivityItem(
    modifier: Modifier = Modifier,
    avatarURL: String,
    title: String,
    user: OtherUser,
    onClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    timeStamp: Long,
) {
    ActivityItem(
        modifier = modifier,
        avatarURL = avatarURL,
        title = title,
        timeStamp = timeStamp,
        description = {
            TextBody(text = "Followed you", color = Color.Gray)
        },
        onClick = onClick,
        action = {
            Button(
                onClick = onActionClick,
                buttonVariant = ButtonVariant.OUTLINED,
                rounded = false,
                disable = true
            ) {
                TextBody(
                    text = if (user.status.followStatus.following) "Following" else "Follow",
                    bold = true,
                    color = if (user.status.followStatus.following) Color.Gray else Color.Black
                )
            }
        })
}

@Preview(showSystemUi = true)
@Composable
private fun ActivityFollowItemPreview() {
    Column {
        FollowActivityItem(
            avatarURL = "",
            title = "sonmuscle",
            timeStamp = 1716181099999,
            user = OtherUser(
                User(),
                OtherUserStatus(FollowStatus(followed = true, following = false))
            )
        )
    }
}
