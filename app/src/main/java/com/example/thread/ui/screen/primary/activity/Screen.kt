package com.example.thread.ui.screen.primary.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun ActivityScreen(threadNavController: ThreadNavController) {
    Column {
        Text(text = "Activity screen")
    }
}
