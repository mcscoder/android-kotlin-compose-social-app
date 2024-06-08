package com.example.thread.ui.component.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.ui.component.button.ThreadActionButtons
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.image.FeedCardImageRow
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextDateTime
import com.example.thread.ui.component.user.UserAvatarClickable
import com.example.thread.ui.component.user.UsernameClickable
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun FeedCardDetails(
    threadNavController: ThreadNavController,
    onFavoriteClick: (isFavorite: Boolean) -> Unit,
    onReplyClick: () -> Unit,
    thread: ThreadResponse,
    showHorizontalDivider: Boolean = true,
) {
    Column(
        modifier = Modifier.padding(12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // User avatar
            UserAvatarClickable(
                avatarURL = thread.user.user.imageUrl,
                onClick = {
                    threadNavController.navigateToUserProfile(thread.user.user.userId)
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            // Username
            UsernameClickable(
                username = thread.user.user.username,
                onClick = { threadNavController.navigateToUserProfile(thread.user.user.userId) })
            Spacer(modifier = Modifier.width(4.dp))
            TextDateTime(timeStamp = thread.content.dateTime.createdAt)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Thread content
        TextBody(text = thread.content.text)
        // Images Row
        if (thread.content.imageUrls.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FeedCardImageRow(imageURLs = thread.content.imageUrls)
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Thread action buttons
        ThreadActionButtons(
            onFavoriteClick = onFavoriteClick,
            onReplyClick = onReplyClick,
            onMoreOptionClick = {},
            thread = thread
        )
    }
    if (showHorizontalDivider) {
        ThreadHorizontalDivider()
    }
}
