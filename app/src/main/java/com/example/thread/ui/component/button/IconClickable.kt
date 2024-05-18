package com.example.thread.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconClickable(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit = {},
    size: Dp = 24.dp,
    padding: Dp = 4.dp,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            },
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifier
            .size(size + padding * 2)
                .padding(padding),
            tint = tint
        )
    }
}
