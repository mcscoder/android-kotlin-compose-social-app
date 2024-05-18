package com.example.thread.ui.screen.primary.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.layout.PullRefreshLayout
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.thread.ThreadDestination
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
        val listState = rememberLazyListState()
        val reachedBottom by remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
            }
        }

        LaunchedEffect(reachedBottom) {
            if (reachedBottom) {
                viewModel.threadsData.retrieveRandomThreadData(3)
            }
        }

        PullRefreshLayout(onRefresh = {
            viewModel.threadsData.retrieveRandomThreadData(
                10,
                reload = true
            )
        }) { pullRefreshState ->
            LazyColumn(state = listState, modifier = Modifier.pullRefresh(pullRefreshState)) {
                itemsIndexed(items = threadsData) { index, thread ->
                    FeedCard(
                        threadNavController = threadNavController,
                        threadData = thread,
                        onFeedCardClick = {
                            ThreadDetailsData.setThreadsData(viewModel.threadsData, index)
                            threadNavController.navigate(ThreadDestination.THREAD_DETAILS.route)
                        },
                        onFavoriteClick = { isFavorite ->
                            viewModel.threadsData.favoritePost(
                                isFavorite = isFavorite,
                                index = index
                            )
                        },
                        onReplyClick = {
                            ThreadDetailsData.setThreadsData(viewModel.threadsData, index)
                            threadNavController.navigate(ThreadDestination.REPLY_THREAD.route)
                        }
                    )
                }
            }
        }
    }
}

// 2. Thread Details Screen [Secondary]
// @Composable
// fun HomeThreadDetailsScreen(threadNavController: ThreadNavController, threadIndex: Int) {
//     Log.d("re-render", "[run when navigated to] HomeThreadDetailScreen")
//
//     val homeViewmodel = HomeViewModelProvider.getInstance()
//     val threadsData = homeViewmodel.threadsData.data.collectAsState().value
//     val thread = threadsData[threadIndex]
//
//     val threadDetailsViewModel = ThreadDetailsViewModelProvider.getInstance(thread)
//
//     ThreadPostDetailsScreen(
//         threadNavController = threadNavController,
//         onFavoriteClick = { isFavorite ->
//             homeViewmodel.threadsData.favoritePost(
//                 isFavorite = isFavorite,
//                 index = threadIndex
//             )
//         },
//         onReplyClick = {
//             threadNavController.navigate("${HomeDestination.REPLY_THREAD.route}/${threadIndex}")
//         },
//         onReplyReplyingClick = { threadReplyingIndex ->
//             threadNavController.navigate("${HomeDestination.REPLY_THREAD_REPLYING.route}/${threadReplyingIndex}")
//         },
//         viewModel = threadDetailsViewModel,
//     )
// }

// 3. Reply Thread Screen [Secondary]
// @Composable
// fun HomeReplyThreadScreen(threadNavController: ThreadNavController, threadIndex: Int) {
//     Log.d("re-render", "[run when navigated to] HomeReplyThreadScreen")
//
//     val homeViewmodel = HomeViewModelProvider.getInstance()
//     val threadsData = homeViewmodel.threadsData.data.collectAsState().value
//     val thread = threadsData[threadIndex]
//
//     val newThreadViewModel = NewThreadViewModelProvider.getInstance(threadNavController)
//     ThreadDetailsViewModelProvider.getInstance(thread)
//
//     NewThreadScreen(
//         threadNavController = threadNavController,
//         viewModel = newThreadViewModel,
//         topBarTitle = "Reply",
//         threadReply = thread,
//         onPostClick = { newThreadViewModel.postThreadReply(thread.content.id) }
//     )
// }

// 4. Reply Thread Replying [Secondary]
// @Composable
// fun HomeReplyThreadReplyingScreen(
//     threadNavController: ThreadNavController,
//     threadReplyingIndex: Int,
// ) {
//     Log.d("re-render", "[run when navigated to] HomeReplyThreadReplyingScreen")
//
//     val threadDetailsViewModel = ThreadDetailsViewModelProvider.getInstance()
//     val threadRepliesData by threadDetailsViewModel.threadRepliesData.collectAsState()
//     val threadReply = threadRepliesData[threadReplyingIndex]
//
//     val newThreadViewModel = NewThreadViewModelProvider.getInstance(threadNavController)
//
//     NewThreadScreen(
//         threadNavController = threadNavController,
//         viewModel = newThreadViewModel,
//         topBarTitle = "Reply",
//         threadReply = threadReply,
//         onPostClick = {
//
//         }
//     )
// }

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ThreadTheme {
        HomeScreen(threadNavController = ThreadNavController(rememberNavController()))
    }
}
