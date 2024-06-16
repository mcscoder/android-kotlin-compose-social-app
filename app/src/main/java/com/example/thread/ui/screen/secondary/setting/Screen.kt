package com.example.thread.ui.screen.secondary.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.button.ButtonVariant
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.icon.IconNormal
import com.example.thread.ui.component.layout.InfiniteScrollLayout
import com.example.thread.ui.component.layout.PullRefreshLayout
import com.example.thread.ui.component.layout.ScaffoldBottomSheet
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun SettingScreen(threadNavController: ThreadNavController) {
    val userPreferences = UserPreferences(LocalContext.current)

    val viewModel: SettingViewModel = viewModel()

    ThreadScaffold(
        topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                title = "Settings",
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column {
                Button(
                    onClick = {
                        viewModel.displaySaved.value = true
                        viewModel.threadsSavedData.getSavedThreads()
                    },
                    buttonVariant = ButtonVariant.TEXT,
                    paddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconNormal(imageVector = Icons.Rounded.BookmarkBorder)
                        Spacer(width = 16.dp)
                        TextBody(text = "Saved")
                    }
                }
                Button(
                    onClick = {
                        viewModel.displayFavorited.value = true
                        viewModel.threadsFavoritedData.getFavoritedThreads()
                    },
                    buttonVariant = ButtonVariant.TEXT,
                    paddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconNormal(imageVector = Icons.Rounded.FavoriteBorder)
                        Spacer(width = 16.dp)
                        TextBody(text = "Your likes")
                    }
                }
                ThreadHorizontalDivider()
                Row(
                    modifier = Modifier.clickable {
                        userPreferences.clearUser()
                    }
                ) {
                    TextBody(
                        text = "Log out",
                        color = Color.Red,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        bold = true
                    )
                }
            }
        }
    }

    // Saved Threads list
    BottomSheetThreadsScreen(
        threadNavController = threadNavController,
        display = viewModel.displaySaved,
        title = "Saved",
        threadsData = viewModel.threadsSavedData,
    )

    // Favorited Threads list
    BottomSheetThreadsScreen(
        threadNavController = threadNavController,
        display = viewModel.displayFavorited,
        title = "Your likes",
        threadsData = viewModel.threadsFavoritedData
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetThreadsScreen(
    threadNavController: ThreadNavController,
    display: MutableState<Boolean>,
    title: String,
    threadsData: ThreadsData,
) {
    ScaffoldBottomSheet(
        display = display,
        title = title,
        doneContent = {},
        onDone = { it() },
    ) { paddingValues, dismiss ->
        val threads = threadsData.data.collectAsState().value

        PullRefreshLayout(onRefresh = { /*TODO*/ }, modifier = Modifier.padding(paddingValues)) {
            InfiniteScrollLayout(modifier = Modifier.pullRefresh(it)) {
                itemsIndexed(threads) { index, item ->
                    FeedCard(threadNavController = threadNavController,
                        threadData = item,
                        onSaveThreadClick = {
                            threadsData.saveThread(index)
                        },
                        onDeleteConfirmed = {
                            threadsData.removeAt(index)
                        })
                }
            }
        }
    }
}
