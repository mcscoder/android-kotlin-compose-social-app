package com.example.thread.ui.component.image

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun FeedCardImageRow(imageURLs: List<String>) {
    Row(
        modifier = Modifier
            .height(300.dp)
            .horizontalScroll(rememberScrollState())
            .clip(shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        imageURLs.forEach { url ->
            ThreadAsyncImage(
                url = url,
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(0.dp, 275.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}
