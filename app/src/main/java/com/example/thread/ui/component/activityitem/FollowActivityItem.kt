package com.example.thread.ui.component.activityitem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.user.User
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.button.ButtonVariant
import com.example.thread.ui.component.text.TextBody

@Composable
fun FollowActivityItem(
    modifier: Modifier = Modifier,
    followActivity: FollowActivity,
    onClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    ActivityItem(
        modifier = modifier,
        avatarURL = followActivity.user.imageUrl,
        title = followActivity.user.username,
        timeStamp = followActivity.dateTime.createdAt,
        description = {
            TextBody(
                text = "${followActivity.user.firstName} ${followActivity.user.lastName}",
                color = Color.Gray
            )
        },
        onClick = onClick,
        action = {
            Button(
                onClick = onActionClick,
                buttonVariant = ButtonVariant.OUTLINED,
                rounded = false,
                disable = true
            ) {
                // TextBody(
                //     text = if (followActivity.user.following) "Following" else "Follow",
                //     bold = true,
                //     color = if (followActivity.user.following) Color.Gray else Color.Black
                // )
            }
        })
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
