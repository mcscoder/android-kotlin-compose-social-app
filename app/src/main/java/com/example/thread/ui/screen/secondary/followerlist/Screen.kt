package com.example.thread.ui.screen.secondary.followerlist

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.thread.ui.component.layout.TabRowLayout
import com.example.thread.ui.component.scaffold.ThreadScaffold

@Composable
fun FollowerListScreen() {
    ThreadScaffold() { paddingValues ->
        TabRowLayout(
            modifier = Modifier.padding(paddingValues),
            tabTitles = listOf("Followers", "Followings"),
            onRefresh = {currentPage ->

            }
        ) { currentPage ->
            when (currentPage) {
                FollowerListScreenTabIndex.FOLLOWER.ordinal -> {
                    // Follower list implementation
                }

                FollowerListScreenTabIndex.FOLLOWING.ordinal -> {
                    // Following list implementation
                }
            }
        }
    }
}

enum class FollowerListScreenTabIndex {
    FOLLOWER,
    FOLLOWING
}
