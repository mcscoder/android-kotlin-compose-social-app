package com.example.thread.ui.component.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.thread.util.DateUtils

@Composable
fun TextDateTime(timeStamp: Long) {
    val formattedTime = DateUtils.formatPostTimestamp(timeStamp)
    TextBody(text = formattedTime, color = Color.Gray)
}
