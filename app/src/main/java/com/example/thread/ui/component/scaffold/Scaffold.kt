package com.example.thread.ui.component.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun ThreadScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit = {}
) {
    Scaffold(modifier = modifier, topBar = topBar, bottomBar = bottomBar, content = content)
}
