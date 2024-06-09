package com.example.thread.ui.screen.secondary.followerlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.thread.ui.component.activityitem.ActivityFollowItem
import com.example.thread.ui.component.layout.TabRowLayout
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.screen.primary.profile.ProfileViewModelProvider

@Composable
fun FollowerListScreen(threadNavController: ThreadNavController, userId: Int) {
    val viewModel = remember {
        FollowListViewModel()
    }
    val user = ProfileViewModelProvider.getInstance(userId).userData.data.collectAsState().value
    val userFollowers = viewModel.followers.data.collectAsState().value
    val userFollowings = viewModel.followings.data.collectAsState().value

    if (user != null) {
        ThreadScaffold(
            topBar = {
                ThreadTopBar(
                    threadNavController = threadNavController,
                    title = user.user.username,
                    showDivider = false
                )
            }
        ) { paddingValues ->
            TabRowLayout(
                modifier = Modifier.padding(paddingValues),
                tabTitles = listOf("Followers", "Followings"),
                onRefresh = { currentPage ->
                    when (currentPage) {
                        FollowerListScreenTabIndex.FOLLOWER.ordinal -> {
                            viewModel.followers.retrieveUserFollowers(userId)
                        }

                        FollowerListScreenTabIndex.FOLLOWING.ordinal -> {
                            viewModel.followings.retrieveUserFollowings(userId)
                        }
                    }
                }
            ) { currentPage ->
                when (currentPage) {
                    FollowerListScreenTabIndex.FOLLOWER.ordinal -> {
                        // Follower list implementation
                        if (userFollowers.isEmpty()) {
                            viewModel.followers.retrieveUserFollowers(userId)
                        } else {
                            itemsIndexed(userFollowers) { index, user ->
                                ActivityFollowItem(
                                    activityFollow = user,
                                    onClick = {
                                        threadNavController.navigateToUserProfile(user.user.user.userId)
                                    },
                                    onActionClick = {
                                        viewModel.followers.onFollowUser(user.user.user.userId) {
                                            viewModel.followers.retrieveUserFollowers(userId)
                                        }
                                    },
                                    showTimeStamp = false
                                )
                            }
                        }
                    }

                    FollowerListScreenTabIndex.FOLLOWING.ordinal -> {
                        // Following list implementation
                        if (userFollowings.isEmpty()) {
                            viewModel.followings.retrieveUserFollowings(userId)
                        } else {
                            itemsIndexed(userFollowings) { index, following ->
                                ActivityFollowItem(
                                    activityFollow = following,
                                    onClick = {
                                        threadNavController.navigateToUserProfile(following.user.user.userId)
                                    },
                                    onActionClick = {
                                        viewModel.followings.onFollowUser(following.user.user.userId) {
                                            viewModel.followings.retrieveUserFollowings(userId)
                                        }
                                    },
                                    showTimeStamp = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class FollowerListScreenTabIndex {
    FOLLOWER,
    FOLLOWING
}
