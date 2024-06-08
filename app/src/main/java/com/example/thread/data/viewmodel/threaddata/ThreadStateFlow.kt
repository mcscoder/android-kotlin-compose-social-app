package com.example.thread.data.viewmodel.threaddata

import com.example.thread.data.model.thread.ThreadResponse
import kotlinx.coroutines.flow.StateFlow

interface ThreadStateFlow {
    val data: StateFlow<List<ThreadResponse>>
}
