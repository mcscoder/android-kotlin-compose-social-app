package com.example.thread.data.viewmodel.threaddata

import com.example.thread.data.model.thread.Thread
import kotlinx.coroutines.flow.StateFlow

interface ThreadStateFlow {
    val data: StateFlow<List<Thread>>
}
