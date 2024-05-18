package com.example.thread.ui.screen.primary.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun SearchScreen(threadNavController: ThreadNavController) {
    Log.d("re-render", "7777777")
    LaunchedEffect(null) {
        // Log.d("re-render", "7777777")
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text(text = "Search")
        }
    }
}
