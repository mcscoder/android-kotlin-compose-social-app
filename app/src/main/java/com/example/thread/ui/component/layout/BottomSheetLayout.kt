package com.example.thread.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.input.NewThreadInput
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextCallOut
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.screen.primary.newthread.NewThreadViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    display: MutableState<Boolean>,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    fun dismiss() {
        coroutineScope.launch {
            sheetState.hide()
            display.value = false
        }
    }

    if (display.value) {
        ModalBottomSheet(
            modifier = modifier.padding(top = 3.dp),
            onDismissRequest = {
                dismiss()
            },
            sheetState = sheetState,
            dragHandle = dragHandle,
            shape = RoundedCornerShape(18.dp)
        ) {
            content() { dismiss() }
            Spacer(height = 8.dp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(true),
    display: MutableState<Boolean>,
    title: String,
    onCancel: (dismiss: () -> Unit) -> Unit = { it() },
    onDone: (dismiss: () -> Unit) -> Unit,
    doneContent: @Composable BoxScope.() -> Unit = {
        TextCallOut(
            text = "Done",
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            bold = true,
        )
    },
    disableDone: Boolean = true,
    content: @Composable ColumnScope.(paddingValues: PaddingValues, dismiss: () -> Unit) -> Unit,
) {
    BottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        display = display,
        dragHandle = null
    ) { dismiss ->
        ThreadScaffold(topBar = {
            ThreadTopBar(
                modifier = Modifier.background(Color.White),
                title = title,
                actions = {
                    Box(
                        modifier = Modifier.clickable(!disableDone) {
                            onDone() {
                                dismiss()
                            }
                        }
                    ) {
                        doneContent()
                    }
                },
                onNavigateUp = {
                    onCancel() {
                        dismiss()
                    }
                }
            )
        }) { paddingValues ->
            content(paddingValues) { dismiss() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewThreadBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: NewThreadViewModel,
    threadNavController: ThreadNavController,
    sheetState: SheetState = rememberModalBottomSheetState(true),
    display: MutableState<Boolean>,
    title: String,
    onCancel: (dismiss: () -> Unit) -> Unit = { it() },
    onPostClick: (dismiss: () -> Unit) -> Unit = { },
    mainThread: ThreadResponse? = null,
) {
    val postButtonDisable = remember {
        mutableStateOf(false)
    }

    ScaffoldBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        display = display,
        title = title,
        onCancel = onCancel,
        onDone = {dismiss ->
            postButtonDisable.value = true
            onPostClick(dismiss)
        },
        disableDone = viewModel.textContent.text.isEmpty() || postButtonDisable.value,
        doneContent = {
            Button(
                onClick = null,
                disable = viewModel.textContent.text.isEmpty() || postButtonDisable.value
            ) {
                TextBody(text = "Post", color = Color.White, bold = true)
            }
        }
    ) { paddingValues, dismiss ->
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
                    onRemoveImageClick = { viewModel.removeImageFile(it) }
                )
            }
        }
    }
}
