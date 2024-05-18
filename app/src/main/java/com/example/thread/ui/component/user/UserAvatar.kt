package com.example.thread.ui.component.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.thread.R
import com.example.thread.data.RetrofitInstance

@Composable
fun UserAvatar(
    avatarURL: String,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    contentScale: ContentScale = ContentScale.Crop,
) {
    // Image(
    //     painter = painterResource(id = R.drawable.yasuo_avt),
    //     contentDescription = null,
    //     modifier = Modifier
    //         .size(36.dp)
    //         .clip(CircleShape),
    // )
    AsyncImage(
        model = RetrofitInstance.publicResourceURL(avatarURL),
        contentDescription = null,
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        contentScale = contentScale
    )
}

@Preview
@Composable
private fun UserAvatarPreview() {
    UserAvatar(avatarURL = "https://images.contentstack.io/v3/assets/blt93c07aad6c2c008a/blt9680fb33fc981b63/63ea6f40ae8b807255191fa6/Yasuo_0.jpg?auto=webp&width=200&height=200")
}
