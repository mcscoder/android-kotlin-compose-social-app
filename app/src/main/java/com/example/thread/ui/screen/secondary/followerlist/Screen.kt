package com.example.thread.ui.screen.secondary.followerlist

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.thread.ui.component.activityitem.FollowActivityItem
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
    Log.d("FollowerListScreen", userId.toString())
    val userData = ProfileViewModelProvider.getInstance(userId).userData.data.collectAsState().value
    val followers = viewModel.followers.data.collectAsState().value
    val followings = viewModel.followings.data.collectAsState().value

    ThreadScaffold(
        topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                title = userData!!.username,
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
                        viewModel.followers.retrieveFollowersData(userId)
                    }

                    FollowerListScreenTabIndex.FOLLOWING.ordinal -> {
                        viewModel.followings.retrieveFollowingsData(userId)
                    }
                }
            }
        ) { currentPage ->
            when (currentPage) {
                FollowerListScreenTabIndex.FOLLOWER.ordinal -> {
                    // Follower list implementation
                    if (followers.isEmpty()) {
                        viewModel.followers.retrieveFollowersData(userId)
                    } else {
                        itemsIndexed(followers) { index, follower ->
                            FollowActivityItem(followActivity = follower)
                        }
                    }
                }

                FollowerListScreenTabIndex.FOLLOWING.ordinal -> {
                    // Following list implementation
                    if (followings.isEmpty()) {
                        viewModel.followings.retrieveFollowingsData(userId)
                    } else {
                        itemsIndexed(followings) { index, following ->
                            FollowActivityItem(followActivity = following)
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
