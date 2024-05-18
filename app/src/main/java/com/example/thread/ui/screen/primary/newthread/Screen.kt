package com.example.thread.ui.screen.primary.newthread

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.model.thread.Thread
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
    viewModel: NewThreadViewModel,
    topBarTitle: String = "New Thread",
    onPostClick: () -> Unit = { viewModel.postThread() },
    threadReply: Thread? = null,
) {
    ThreadScaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                title = topBarTitle,
                actions = {
                    Button(
                        onClick = onPostClick,
                        disable = viewModel.textContent.text.isEmpty()
                    ) {
                        TextBody(text = "Post", color = Color.White, bold = true)
                    }
                },
                showBackButton = threadReply != null
            )
        },
        bottomBar = {
            TextBody(text = "Are you sure bro?")
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                if (threadReply != null) {
                    FeedCard(
                        threadNavController = threadNavController,
                        threadData = threadReply,
                        showActionButton = false
                    )
                }
                NewThreadInput(
                    text = viewModel.textContent,
                    onTextChange = { viewModel.updateText(it) },
                    imageFiles = viewModel.imageFiles,
                    onImageFilesChange = { viewModel.updateImageFiles(it) },
                    onRemoveImageClick = { viewModel.removeImageFile(it) }
                )
            }
        }
    }
}

@Composable
fun ReplyThreadScreen(threadNavController: ThreadNavController) {
    val threadsData = ThreadDetailsData.threadsData
    val threadIndex = ThreadDetailsData.threadIndex
    if (threadsData != null && threadIndex != null) {
        val thread = threadsData.data.collectAsState().value[threadIndex]
        val viewModel = remember {
            NewThreadViewModel(threadNavController)
        }
        NewThreadScreen(
            threadNavController = threadNavController,
            viewModel = viewModel,
            topBarTitle = "Reply",
            threadReply = thread,
            onPostClick = {
                viewModel.postThreadReply(thread.content.id)
            }
        )
    }
}

@Composable
fun ReplyThreadReplyingScreen(threadNavController: ThreadNavController) {
    val threadRepliesData = ThreadDetailsData.threadRepliesData
    val threadReplyIndex = ThreadDetailsData.threadReplyIndex
    if (threadRepliesData != null && threadReplyIndex != null) {
        val threadReply = threadRepliesData.data.collectAsState().value[threadReplyIndex]
        val viewModel = remember {
            NewThreadViewModel(threadNavController)
        }
        NewThreadScreen(
            threadNavController = threadNavController,
            viewModel = viewModel,
            topBarTitle = "Reply",
            threadReply = threadReply,
            onPostClick = {
                viewModel.postThreadReplyingReply(threadReply.content.id)
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
