package com.example.thread.ui.screen.primary.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.icon.IconNormal
import com.example.thread.ui.component.input.TextField
import com.example.thread.ui.component.layout.InfiniteScrollLayout
import com.example.thread.ui.component.layout.PullRefreshLayout
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.search.RecentSearchItem
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextHeadLine
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.search.SearchDestination

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(threadNavController: ThreadNavController) {
    val textFieldValue = remember {
        mutableStateOf(TextFieldValue())
    }

    val userPreferences = UserPreferences(LocalContext.current)
    val searchHistory = userPreferences.searchHistory.collectAsState(initial = emptyList()).value.reversed()


    PullRefreshLayout(onRefresh = { /*TODO*/ }) {
        InfiniteScrollLayout(modifier = Modifier) {
            item {
                TextHeadLine(
                    text = "Search",
                    fontSize = 32.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            stickyHeader {
                TextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    value = textFieldValue.value,
                    onValueChange = { textFieldValue.value = it },
                    placeHolder = "Search",
                    padding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
                    shape = RoundedCornerShape(8.dp),
                    borderColor = Color.Transparent,
                    startIcon = {
                        IconNormal(
                            modifier = Modifier.padding(start = 8.dp),
                            imageVector = Icons.Filled.Search,
                            size = 20.dp,
                            tint = Color.Gray
                        )
                    }
                ) {
                    threadNavController.navigate(
                        "${SearchDestination.RESULT_DESTINATION.route}/${textFieldValue.value.text}"
                    )
                    userPreferences.addSearchHistoryItem(textFieldValue.value.text)
                }
            }
            item {
                Row(modifier = Modifier.padding(16.dp)) {
                    TextBody(text = "Recent", bold = true)
                    Spacer(modifier = Modifier.weight(1f))
                    TextBody(
                        text = "Clear",
                        modifier = Modifier.clickable {
                            userPreferences.clearSearchHistory()
                        },
                        bold = true,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            itemsIndexed(searchHistory) { index, text ->
                RecentSearchItem(text = text, onClick = {
                    textFieldValue.value = TextFieldValue(text)
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchResultsScreen(threadNavController: ThreadNavController, searchText: String) {
    val viewModel = remember {
        SearchResultsViewModel(searchText)
    }

    val threads = viewModel.threadsData.data.collectAsState().value

    ThreadScaffold(
        topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                title = "Results for ${searchText}",
            )
        }
    ) { paddingValues ->
        PullRefreshLayout(
            modifier = Modifier.padding(paddingValues),
            onRefresh = { /*TODO*/ }) { pullRefreshState ->
            InfiniteScrollLayout(
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxSize(),
            ) {
                itemsIndexed(items = threads) {index, thread ->
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
