package com.example.thread.ui.screen.primary.newthread

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.input.NewThreadInput
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.thread.ThreadDetailsData
import com.example.thread.ui.theme.ThreadTheme

@Composable
fun NewThreadScreen(
    threadNavController: ThreadNavController,
    viewModel: NewThreadViewModel = viewModel(),
    topBarTitle: String = "New Thread",
    onPostClick: () -> Unit = { viewModel.postThread() },
    onNavigateUp: () -> Unit = {},
    mainThread: ThreadResponse? = null,
) {
    val postButtonDisable = remember {
        mutableStateOf(false)
    }

    ThreadScaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                title = topBarTitle,
                actions = {
                    Button(
                        onClick = {
                            postButtonDisable.value = true
                            onPostClick()
                        },
                        disable = viewModel.textContent.text.isEmpty() || postButtonDisable.value
                    ) {
                        TextBody(text = "Post", color = Color.White, bold = true)
                    }
                },
                showBackButton = mainThread != null,
                onNavigateUp = onNavigateUp
            )
        },
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                if (mainThread != null) {
                    FeedCard(
                        threadNavController = threadNavController,
                        threadData = mainThread,
                        showActionButton = false,
                        showVerticalDivider = true
                    )
                }
                NewThreadInput(
                    text = viewModel.textContent,
                    onTextChange = { viewModel.updateText(it) },
                    imageFiles = viewModel.imageFiles,
                    onImageFilesChange = { viewModel.updateImageFiles(it) },
                    onRemoveImageFileClick = { viewModel.removeImageFile(it) }
                )
            }
        }
    }
}

@Composable
fun ReplyToThreadScreen(
    threadNavController: ThreadNavController,
    threadsDataIndex: Int,
    threadIndex: Int,
    threadType: Int,
) {
    val threadsData = ThreadDetailsData.getThreadsData(threadsDataIndex)
    if (threadsData != null) {
        val thread = threadsData.data.collectAsState().value[threadIndex]
        val viewModel = remember {
            NewThreadViewModel()
        }
        NewThreadScreen(
            threadNavController = threadNavController,
            viewModel = viewModel,
            topBarTitle = "Reply",
            mainThread = thread,
            onPostClick = {
                viewModel.postReply(threadType, thread.content.threadId) {
                    threadsData.updateAt(threadIndex)
                }
            },
            onNavigateUp = {
                ThreadDetailsData.removeThreadsDataAt(threadsDataIndex)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewThreadScreenPreview() {
    ThreadTheme(darkTheme = false) {
        NewThreadScreen(
            threadNavController = ThreadNavController(rememberNavController()),
            viewModel()
        )
    }
}
