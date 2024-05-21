package com.example.thread.ui.component.common

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThreadHorizontalDivider() {
    Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(217, 217, 217))
}

@Composable
fun ThreadVerticalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(2.dp)
            .clip(CircleShape),
        color = Color.LightGray,
    )
}
