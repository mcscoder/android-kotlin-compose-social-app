@file:Suppress("UNUSED_EXPRESSION")

package com.example.thread.ui.component.layout

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.modifier.NoRippleTheme
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
    content: LazyListScope.(pageIndex: Int) -> Unit = {},
) {
    var pageIndex by remember {
        mutableIntStateOf(initialPage)
    }
    val listState = rememberLazyListState()

    LaunchedEffect(pageIndex) {
        val firstVisibleItemIndex = listState.firstVisibleItemIndex
        if (firstVisibleItemIndex >= TabRowLayoutDefaults.TabRowItemIndex) {
            listState.animateScrollToItem(TabRowLayoutDefaults.TabRowItemIndex)
        }
    }

    // For doing something when reached to the bottom of the page
    LaunchedEffect(listState.canScrollForward) {
        if (!listState.canScrollForward) {
            Log.d("can't scroll more", "hell yeah")
            // Do something here to fetch more Threads
        }
    }

    PullRefreshLayout(
        refreshing = refreshing,
        onRefresh = { onRefresh(pageIndex) }
    ) { pullRefreshState ->
        LazyColumn(modifier = modifier.pullRefresh(pullRefreshState), state = listState) {
            item { title() }
            stickyHeader {
                TabRow(
                    selectedTabIndex = pageIndex,
                    indicator = { tabPositions ->
                        // It's worked like this by default
                        // But to customize them we need to replace them by another
                        // With modified UI
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pageIndex]),
                            color = Color.Black,
                            height = 2.dp
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        // // Prevent button ripple effect when clicked
                        // CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        Tab(selected = pageIndex == index,
                            onClick = {
                                pageIndex = index
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
            content(pageIndex)
        }
    }
}

object TabRowLayoutDefaults {
    const val TabRowItemIndex = 1
    const val HorizontalPagerItemIndex = 2
}
