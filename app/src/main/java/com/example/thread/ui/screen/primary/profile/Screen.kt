package com.example.thread.ui.screen.primary.profile

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.model.user.User
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.button.ButtonVariant
import com.example.thread.ui.component.button.IconClickable
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.layout.TabRowLayout
import com.example.thread.ui.component.layout.lazyThreadDetailsCard
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextHeadLine
import com.example.thread.ui.component.user.UserAvatar
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.myprofile.MyProfileDestination
import com.example.thread.ui.navigation.thread.ThreadDestination
import com.example.thread.ui.navigation.thread.ThreadDetailsData
import com.example.thread.ui.screen.GlobalViewModelProvider

@Composable
fun ProfileHeader(userData: User, myProfile: Boolean) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp)
            ) {
                TextHeadLine(text = "${userData.firstName} ${userData.lastName}")
                Spacer(modifier = Modifier.height(8.dp))
                TextBody(text = userData.username)
            }
            UserAvatar(
                avatarURL = userData.avatarURL!!,
                size = 64.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextBody(text = "999 followers", color = Color.Gray)
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (myProfile) {
                Button(
                    onClick = { /*TODO*/ },
                    rounded = false,
                    modifier = Modifier.weight(1f),
                    buttonVariant = ButtonVariant.OUTLINED
                ) {
                    TextBody(text = "Edit profile", bold = true)
                }
                Button(
                    onClick = { /*TODO*/ },
                    rounded = false,
                    modifier = Modifier.weight(1f),
                    buttonVariant = ButtonVariant.OUTLINED
                ) {
                    TextBody(text = "Share profile", bold = true)
                }
            } else {
                Button(
                    onClick = { /*TODO*/ },
                    rounded = false,
                    modifier = Modifier.weight(1f)
                ) {
                    TextBody(text = "Follow", color = Color.White, bold = true)
                }
                Button(
                    onClick = { /*TODO*/ },
                    rounded = false,
                    modifier = Modifier.weight(1f),
                    buttonVariant = ButtonVariant.OUTLINED
                ) {
                    TextBody(text = "Mention", bold = true)
                }
            }
        }
    }
}

// 1. Profile Screen [Primary]
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    threadNavController: ThreadNavController,
    userId: Int,
    myProfile: Boolean = userId == GlobalViewModelProvider.getUserId(),
) {
    val viewModel: ProfileViewModel =
        ProfileViewModelProvider.getInstance(userId)

    val threadsData = viewModel.threadsData.data.collectAsState().value
    val userData = viewModel.userData.data.collectAsState().value
    val repliesData = viewModel.userRepliesData.data.collectAsState().value

    if (userData != null) {
        Log.d("re-render", "ProfileScreen re-render")
        ThreadScaffold(topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                actions = {
                    IconClickable(imageVector = Icons.Outlined.Notifications)
                    Spacer(modifier = Modifier.width(10.dp))
                    IconClickable(imageVector = Icons.Outlined.MoreHoriz, onClick = {
                        threadNavController.navigate(MyProfileDestination.SETTING.route)
                    })
                },
                showDivider = false,
                showBackButton = !myProfile
            )
        }) { paddingValues ->
            TabRowLayout(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                title = { ProfileHeader(userData, myProfile) },
                tabTitles = listOf("Threads", "Replies"),
                onRefresh = { currentPage ->
                    viewModel.retrieveUserData()
                    when (currentPage) {
                        0 -> {
                            viewModel.retrieveThreadData()
                        }

                        1 -> {
                            viewModel.retrieveRepliesData()
                        }
                    }
                }
            ) { pageIndex ->
                when (pageIndex) {
                    0 -> {
                        itemsIndexed(threadsData) { index, thread ->
                            FeedCard(
                                threadNavController = threadNavController,
                                threadData = thread,
                                onFeedCardClick = {
                                    ThreadDetailsData.setThreadsData(
                                        viewModel.threadsData,
                                        index
                                    )
                                    threadNavController.navigate(ThreadDestination.THREAD_DETAILS.route)
                                },
                                onFavoriteClick = { isFavorite ->
                                    viewModel.threadsData.favoritePost(
                                        isFavorite = isFavorite,
                                        index = index
                                    )
                                },
                                onReplyClick = {
                                    ThreadDetailsData.setThreadsData(
                                        viewModel.threadsData,
                                        index
                                    )
                                    threadNavController.navigate(ThreadDestination.REPLY_THREAD.route)
                                }
                            )
                        }
                    }

                    1 -> {
                        if (repliesData.isEmpty()) {
                            viewModel.retrieveRepliesData()
                        } else {
                            repliesData.forEachIndexed { index, replies ->
                                lazyThreadDetailsCard(
                                    threadNavController = threadNavController,
                                    onFavoriteClick = { isFavorite ->
                                        viewModel.userRepliesData.favoritePost(
                                            isFavorite,
                                            index
                                        )
                                    },
                                    onFavoriteReplyClick = { isFavorite: Boolean, threadReplyIndex: Int ->
                                        viewModel.userRepliesData.favoriteThreadReply(
                                            isFavorite,
                                            threadReplyIndex,
                                            index
                                        )
                                    },
                                    onReplyClick = { },
                                    onReplyReplyingClick = {},
                                    onReplyCardClick = {},
                                    thread = replies.mainThread,
                                    threadReplies = replies.threadReplies,
                                    showBottomDivider = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// 2. Thread Details Screen [Secondary]
// @Composable
// fun ProfileThreadDetailsScreen(
//     threadNavController: ThreadNavController,
//     threadIndex: Int,
//     myProfile: Boolean = false,
// ) {
//     val profileViewModel: ProfileViewModel? = if (myProfile) {
//         ProfileViewModelProvider.getMyProfileInstanceOrNull()
//     } else {
//         ProfileViewModelProvider.getMyProfileInstanceOrNull()
//     }
//
//     if (profileViewModel != null) {
//         val threadsData = profileViewModel.threadsData.data.collectAsState().value
//         val thread = threadsData[threadIndex]
//
//         val threadDetailsViewModel = ThreadDetailsViewModelProvider.getInstance(thread)
//
//         ThreadPostDetailsScreen(
//             threadNavController = threadNavController,
//             onFavoriteClick = { isFavorite ->
//                 profileViewModel.threadsData.favoritePost(
//                     isFavorite = isFavorite,
//                     index = threadIndex
//                 )
//             },
//             onReplyClick = {
//                 threadNavController.navigate("${MyProfileDestination.REPLY_THREAD.route}/${threadIndex}")
//             },
//             onReplyReplyingClick = { },
//             viewModel = threadDetailsViewModel
//         )
//     }
// }

// 3. Reply Thread Screen [Secondary]
// @Composable
// fun ProfileReplyThreadScreen(
//     threadNavController: ThreadNavController,
//     threadIndex: Int,
//     myProfile: Boolean = false,
// ) {
//     val profileViewModel: ProfileViewModel? = if (myProfile) {
//         ProfileViewModelProvider.getMyProfileInstanceOrNull()
//     } else {
//         ProfileViewModelProvider.getMyProfileInstanceOrNull()
//     }
//
//     if (profileViewModel != null) {
//         val threadsData = profileViewModel.threadsData.data.collectAsState().value
//         val thread = threadsData[threadIndex]
//
//         val newThreadViewModel = NewThreadViewModelProvider.getInstance(threadNavController)
//         ThreadDetailsViewModelProvider.getInstance(thread)
//
//         NewThreadScreen(
//             threadNavController = threadNavController,
//             viewModel = newThreadViewModel,
//             topBarTitle = "Reply",
//             threadReply = thread,
//             onPostClick = { newThreadViewModel.postThreadReply(thread.content.id) }
//         )
//     }
// }

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(threadNavController = ThreadNavController(rememberNavController()), 1)
}
