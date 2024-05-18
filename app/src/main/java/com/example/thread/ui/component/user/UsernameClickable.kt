package com.example.thread.ui.component.user

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import com.example.thread.ui.component.text.TextBody

@Composable
fun UsernameClickable(username: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextBody(
        text = username,
        textDecoration = TextDecoration.Underline,
        bold = true,
        modifier = Modifier.clickable { onClick() }, // Navigate to user profile
        lineHeight = TextUnit.Unspecified
    )
}
