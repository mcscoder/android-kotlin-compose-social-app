package com.example.thread.ui.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.text.TextBody

@Composable
fun TitleDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    placeholder: String = "",
) {
    Column(modifier = modifier) {
        TextBody(text = title, bold = true)
        Spacer(height = 4.dp)
        if (description != null) {
            TextBody(text = description, fontSize = 13.sp)
        } else {
            TextBody(text = placeholder, color = Color.Gray, fontSize = 13.sp)
        }
        ThreadHorizontalDivider(modifier = Modifier.padding(top = 12.dp))
    }
}
