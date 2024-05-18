package com.example.thread.ui.component.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun Spacer(width: Dp = Dp.Unspecified, height: Dp = Dp.Unspecified, modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Spacer(
        modifier = modifier.size(
            width = width,
            height = height
        )
    )
}
