package com.example.thread.data.viewmodel.threaddata

import com.example.thread.data.model.favorite.Favorite
import com.example.thread.data.model.thread.Thread
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ThreadsData : MainThreads, ThreadReplies, ThreadReplyingReplies {
    private val _data = MutableStateFlow<List<Thread>>(emptyList())
    override val data: StateFlow<List<Thread>> = _data.asStateFlow()
    private val currentUserId = GlobalViewModelProvider.getInstance().getUser().id

    private val threadRepository = ThreadRepository()

    private fun favorite(isFavorite: Boolean, index: Int) {
        _data.update { data ->
            val updatedData = data.toMutableList()
            updatedData[index] = updatedData[index].copy(
                favorite = Favorite(
                    favoriteCount = updatedData[index].favorite.favoriteCount + if (isFavorite) 1 else -1,
                    isFavorite = isFavorite
                )
            )
            updatedData
        }
    }

    // 1. ----- Main Thread
    override fun favoritePost(isFavorite: Boolean, index: Int) {
        favorite(isFavorite, index)
        // Sync to the database on the server
        CoroutineScope(Dispatchers.IO).launch {
            val threadId = data.value[index].content.id
            threadRepository.favoriteThread(threadId, isFavorite, currentUserId)
        }
    }

    override fun retrieveThreadById(threadId: Int, action: CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val thread = threadRepository.getThreadById(threadId, currentUserId)
            if (thread != null) {
                _data.update { listOf(thread) }
                action()
            }
        }
    }

    override fun retrieveUserThreadsData(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val threads = threadRepository.getThreadsByUser(userId)
            if (threads != null) {
                _data.update { threads }
            }

        }
    }

    override fun retrieveRandomThreadData(count: Int, reload: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val randomThreads = threadRepository.getRandomThreads(count, currentUserId)
            if (randomThreads != null) {
                _data.update { threadList ->
                    if (reload) {
                        randomThreads
                    } else {
                        val updatedThreadList = threadList.toMutableList()
                        updatedThreadList.addAll(randomThreads)
                        updatedThreadList
                    }
                }
            }

        }
    }

    // 2. ----- Thread Reply
    override fun favoriteThreadReply(isFavorite: Boolean, index: Int) {
        favorite(isFavorite, index)
        // Sync to the database on the server
        CoroutineScope(Dispatchers.IO).launch {
            val threadReplyId = data.value[index].content.id
            threadRepository.favoriteThreadReply(
                threadReplyId,
                isFavorite,
                currentUserId
            )
        }
    }

    override fun retrieveThreadRepliesData(threadId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val threadReplies = threadRepository.getThreadReplies(threadId, currentUserId)
            if (threadReplies != null) {
                _data.update { threadReplies }
            }
        }
    }

    // 3. ---- Thread Replying Reply
    override fun favoriteThreadReplyingReply(isFavorite: Boolean, index: Int) {
        favorite(isFavorite, index)
        CoroutineScope(Dispatchers.IO).launch {
            val threadReplyingReplyId = data.value[index].content.id
            threadRepository.favoriteThreadReplyingReply(
                threadReplyingReplyId,
                isFavorite,
                currentUserId
            )
        }
    }

    override fun retrieveThreadReplyingReplies(threadReplyId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val threadReplyingReplies = threadRepository.getThreadReplyingReplies(threadReplyId)
            if (threadReplyingReplies != null) {
                _data.update { threadReplyingReplies }
            }
        }
    }
}
