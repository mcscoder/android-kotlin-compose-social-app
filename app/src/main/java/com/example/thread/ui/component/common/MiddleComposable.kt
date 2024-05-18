package com.example.thread.ui.component.common

import androidx.compose.runtime.Composable

@Composable
fun MiddleComposable(content: @Composable () -> Unit) {
    content()
}
