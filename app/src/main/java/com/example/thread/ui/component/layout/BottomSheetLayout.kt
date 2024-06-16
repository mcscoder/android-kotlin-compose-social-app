package com.example.thread.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.text.TextCallOut
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
    doneContent: @Composable BoxScope.() -> Unit = {
        TextCallOut(
            text = "Done",
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            bold = true,
        )
    },
    onCancel: (dismiss: () -> Unit) -> Unit = { it() },
    onDone: (dismiss: () -> Unit) -> Unit,
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
                        modifier = Modifier.clickable {
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
