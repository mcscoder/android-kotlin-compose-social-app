package com.example.thread.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.text.TextCallOut
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    display: Boolean,
    onDismiss: () -> Unit,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable ColumnScope.() -> Unit,
) {
    if (display) {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 3.dp),
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            dragHandle = dragHandle,
            shape = RoundedCornerShape(18.dp)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(true),
    display: Boolean,
    onDismiss: () -> Unit,
    title: String,
    doneContent: @Composable BoxScope.() -> Unit = {
        TextCallOut(
            text = "Done",
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            bold = true,
        )
    },
    onCancel: () -> Unit = {},
    onDone: () -> Unit = {},
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    BottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        display = display,
        onDismiss = onDismiss,
        dragHandle = null
    ) {
        ThreadScaffold(topBar = {
            ThreadTopBar(
                modifier = Modifier.background(Color.White),
                title = title,
                actions = {
                    Box(
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                onDone()
                                onCancel()
                            }
                        }
                    ) {
                        doneContent()
                    }
                },
                onNavigateUp = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onCancel()
                    }
                }
            )
        }) { paddingValues ->
            content(paddingValues)
        }
    }
}
