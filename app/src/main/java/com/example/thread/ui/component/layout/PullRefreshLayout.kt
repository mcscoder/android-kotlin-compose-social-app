package com.example.thread.ui.component.layout

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshLayout(
    modifier: Modifier = Modifier,
    refreshing: Boolean = false,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.(PullRefreshState) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )

    Box(modifier = modifier) {
        content(pullRefreshState)
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}
