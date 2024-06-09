package com.example.thread.ui.screen.primary.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.model.thread.ThreadType
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.layout.InfiniteScrollLayout
import com.example.thread.ui.component.layout.PullRefreshLayout
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.thread.ThreadDetailsData
import com.example.thread.ui.theme.ThreadTheme

// 1. Home Screen [Primary]
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    threadNavController: ThreadNavController,
) {
    val viewModel = remember { HomeViewModelProvider.getInstance() }
    val threadsData = viewModel.threadsData.data.collectAsState().value

    if (threadsData.isNotEmpty()) {
        PullRefreshLayout(onRefresh = {
            viewModel.threadsData.getRandomThreads(true)
        }) { pullRefreshState ->
            InfiniteScrollLayout(
                modifier = Modifier.pullRefresh(pullRefreshState).fillMaxSize(),
                onReachedLastVisibleItem = {
                    viewModel.threadsData.getRandomThreads()
                }
            ) {
                itemsIndexed(items = threadsData) { index, thread ->
                    FeedCard(
                        threadNavController = threadNavController,
                        threadData = thread,
                        onFeedCardClick = {
                            threadNavController.navigateToThreadDetails(viewModel.threadsData, index)
                        },
                        onFavoriteClick = { isFavorite ->
                            viewModel.threadsData.favoriteThread(
                                isFavorited = isFavorite,
                                index = index
                            )
                        },
                        onReplyClick = {
                            threadNavController.navigateToReplyToThread(
                                viewModel.threadsData,
                                index,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ThreadTheme {
        HomeScreen(threadNavController = ThreadNavController(rememberNavController()))
    }
}
