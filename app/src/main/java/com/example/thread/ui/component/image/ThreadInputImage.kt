package com.example.thread.ui.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.thread.ui.component.button.IconClickable

@Composable
fun ThreadInputImage(byteArray: ByteArray, onRemoveImageClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(byteArray),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(0.dp, 200.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
        Box(modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 8.dp, end = 8.dp)) {
            IconClickable(
                imageVector = Icons.Filled.RemoveCircleOutline,
                onClick = onRemoveImageClick
            )
        }
    }
}
