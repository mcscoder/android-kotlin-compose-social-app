package com.example.thread.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.thread.data.RetrofitInstance

@Composable
fun ThreadAsyncImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = RetrofitInstance.publicResourceURL(url),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
