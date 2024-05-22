package com.example.thread.ui.component.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.model.common.DateTime
import com.example.thread.data.model.favorite.Favorite
import com.example.thread.data.model.thread.Thread
import com.example.thread.data.model.thread.ThreadEntity
import com.example.thread.data.model.user.User
import com.example.thread.ui.component.button.ThreadActionButtons
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.common.ThreadVerticalDivider
import com.example.thread.ui.component.image.FeedCardImageRow
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextDateTime
import com.example.thread.ui.component.user.UserAvatarClickable
import com.example.thread.ui.component.user.UsernameClickable
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.screen.GlobalViewModelProvider
import com.example.thread.ui.theme.ThreadTheme

@Composable
fun FeedCard(
    threadNavController: ThreadNavController,
    threadData: Thread,
    onFeedCardClick: () -> Unit = {},
    onFavoriteClick: (isFavorite: Boolean) -> Unit = {},
    onReplyClick: () -> Unit = {},
    ableToReply: Boolean = true,
    showActionButton: Boolean = true,
    showVerticalDivider: Boolean = false,
    showHorizontalDivider: Boolean = true,
) {
    // Navigate to ThreadPostDetails by clicked to the FeedCard
    Box(modifier = Modifier.clickable { onFeedCardClick() }) {
        Column {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UserAvatarClickable(
                        avatarURL = threadData.user.avatarURL!!,
                        isFollowed = threadData.user.following,
                        onClick = { threadNavController.navigateToUserProfile(threadData.user.id) }
                    )
                    if (showVerticalDivider) {
                        ThreadVerticalDivider()
                    }
                }
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        // User name
                        UsernameClickable(
                            username = threadData.user.username,
                            onClick = { threadNavController.navigateToUserProfile(threadData.user.id) }
                        )
                        // Post time (ago)
                        TextDateTime(timeStamp = threadData.dateTime.createdAt)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    // Thread content
                    FeedCardContent(threadData)
                    if (showActionButton) {
                        Spacer(modifier = Modifier.height(12.dp))
                        // Thread action buttons
                        ThreadActionButtons(
                            onFavoriteClick = onFavoriteClick,
                            onReplyClick = onReplyClick,
                            onMoreOptionClick = {},
                            thread = threadData,
                            ableToReply = ableToReply
                        )
                    }
                    if (threadData.replyCount > 0 && showActionButton) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(modifier = Modifier.clickable {
                            onFeedCardClick()
                        }) {
                            TextBody(
                                text = "${threadData.replyCount} replies",
                                bold = true,
                                color = Color.Blue,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
            if (showActionButton && showHorizontalDivider) ThreadHorizontalDivider()
        }
    }
}

@Composable
fun FeedCardContent(threadData: Thread) {
    Column {
        // Thread content
        TextBody(text = threadData.content.text!!)
        if (threadData.imageURLs.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FeedCardImageRow(imageURLs = threadData.imageURLs)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedCardPreview() {
    ThreadTheme(darkTheme = false) {
        FeedCard(
            ThreadNavController(rememberNavController()),
            feedCardSampleData
        )
    }
}

// Sample data for development
val feedCardSampleData = Thread(
    content = ThreadEntity(
        id = 1,
        text = "Death is like the wind, always by my side.\n" +
                "Do not fear the darkness, fear the nothing after I am gone.\n" +
                "Is a leaf's only purpose to fall?\n" +
                "Honor is in the heart, not the name.\n" +
                "The road to ruin is shorter than you think.\n" +
                "My honor is left a long time ago.\n" +
                "Justice. That's a pretty word.",
        userId = 1
    ),
    user = User(
        id = 1,
        username = "yasuo",
        firstName = "yasuo",
        lastName = "hasagi",
        avatarURL = "https://images.contentstack.io/v3/assets/blt93c07aad6c2c008a/blt9680fb33fc981b63/63ea6f40ae8b807255191fa6/Yasuo_0.jpg?auto=webp&width=200&height=200",
        following = false
    ),
    favorite = Favorite(
        favoriteCount = 10,
        isFavorite = true
    ),
    replyCount = 0,
    dateTime = DateTime(1715217625868, 1715217625868)
)
