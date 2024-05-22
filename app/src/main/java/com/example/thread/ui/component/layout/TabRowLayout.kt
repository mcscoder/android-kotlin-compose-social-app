@file:Suppress("UNUSED_EXPRESSION")

package com.example.thread.ui.component.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.text.TextBody
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TabRowLayout(
    modifier: Modifier = Modifier,
    title: @Composable LazyItemScope.() -> Unit = {},
    initialPage: Int = 0,
    tabTitles: List<String> = listOf("Hello", "There", "World"),
    refreshing: Boolean = false,
    onRefresh: (currentPage: Int) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    content: LazyListScope.(pageIndex: Int) -> Unit,
) {
    var currentPageIndex by remember {
        mutableIntStateOf(initialPage)
    }
    val coroutineScope = rememberCoroutineScope()

    PullRefreshLayout(
        refreshing = refreshing,
        onRefresh = { onRefresh(currentPageIndex) }
    ) { pullRefreshState ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            state = listState
        ) {
            item { title() }
            stickyHeader {
                TabRow(
                    selectedTabIndex = currentPageIndex,
                    indicator = { tabPositions ->
                        // It's worked like this by default
                        // But to customize them we need to replace them by another
                        // With modified UI
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[currentPageIndex]),
                            color = Color.Black,
                            height = 2.dp
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        // // Prevent button ripple effect when clicked
                        // CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        Tab(selected = currentPageIndex == index,
                            onClick = {
                                if (currentPageIndex != index) {
                                    currentPageIndex = index
                                    coroutineScope.launch {
                                        val firstVisibleItemIndex = listState.firstVisibleItemIndex
                                        if (firstVisibleItemIndex >= TabRowLayoutDefaults.TabRowItemIndex) {
                                            listState.scrollToItem(TabRowLayoutDefaults.TabRowItemIndex)
                                        }
                                    }
                                }

                            },
                            selectedContentColor = Color.Black,
                            unselectedContentColor = Color.Gray,
                            text = {
                                TextBody(
                                    text = title,
                                    bold = true,
                                    color = LocalContentColor.current
                                )
                            }
                        )
                        // }
                    }
                }
            }
            content(currentPageIndex)
        }
    }
}

object TabRowLayoutDefaults {
    const val TabRowItemIndex = 1
    const val HorizontalPagerItemIndex = 2
}
