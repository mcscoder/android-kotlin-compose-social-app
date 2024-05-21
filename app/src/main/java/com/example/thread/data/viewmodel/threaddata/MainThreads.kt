package com.example.thread.data.viewmodel.threaddata

import kotlinx.coroutines.CoroutineScope

interface MainThreads : ThreadStateFlow {
    fun favoritePost(isFavorite: Boolean, index: Int)
    fun retrieveUserThreadsData(userId: Int)
    fun retrieveRandomThreadData(count: Int, reload: Boolean = false)

    fun retrieveThreadById(threadId: Int, action: CoroutineScope.() -> Unit = {})
}
