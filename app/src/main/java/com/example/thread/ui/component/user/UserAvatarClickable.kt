package com.example.thread.ui.component.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.icon.IconNormal

@Composable
fun UserAvatarClickable(avatarURL: String, isFollowed: Boolean = false, onClick: () -> Unit) {
    // Follow user by click to the avatar
    Box(modifier = Modifier.clickable { onClick() }) {
        // User avatar
        UserAvatar(avatarURL)
        // Only show up for unfollowing user
        if (!isFollowed) {
            // Mini circle plus
            Surface(
                modifier = Modifier.align(Alignment.BottomEnd),
                color = Color.White,
                shape = CircleShape
            ) {
                IconNormal(
                    imageVector = Icons.Filled.AddCircle,
                    size = 16.dp
                )
            }
        }
    }
}
