package com.example.thread.data.viewmodel

import com.example.thread.data.model.favorite.Favorite
import com.example.thread.data.model.thread.Thread
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface ThreadStateFlow {
    val data: StateFlow<List<Thread>>
}

interface MainThreads : ThreadStateFlow {
    fun favoritePost(isFavorite: Boolean, index: Int)
    fun retrieveThreadData(userId: Int)
    fun retrieveRandomThreadData(count: Int, reload: Boolean = false)
}

interface ThreadReplies : ThreadStateFlow {
    fun favoriteThreadReply(isFavorite: Boolean, index: Int)
    fun retrieveThreadRepliesData(threadId: Int)
}

interface ThreadReplyingReplies : ThreadStateFlow {
    fun favoriteThreadReplyingReply(isFavorite: Boolean, index: Int)
    fun retrieveThreadReplyingReplies(threadReplyId: Int)
}

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
        runBlocking {
            launch(context = Dispatchers.IO) {
                val threadId = data.value[index].content.id
                threadRepository.favoriteThread(threadId, isFavorite, currentUserId)
            }
        }
    }

    override fun retrieveThreadData(userId: Int) {
        runBlocking {
            launch(context = Dispatchers.IO) {
                val threads = threadRepository.getThreadsByUser(userId)
                if (threads != null) {
                    _data.update { threads }
                }
            }
        }
    }

    override fun retrieveRandomThreadData(count: Int, reload: Boolean) {
        runBlocking {
            launch(context = Dispatchers.IO) {
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
    }

    // 2. ----- Thread Reply
    override fun favoriteThreadReply(isFavorite: Boolean, index: Int) {
        favorite(isFavorite, index)
        // Sync to the database on the server
        runBlocking {
            launch(context = Dispatchers.IO) {
                val threadReplyId = data.value[index].content.id
                threadRepository.favoriteThreadReply(
                    threadReplyId,
                    isFavorite,
                    currentUserId
                )
            }
        }
    }

    override fun retrieveThreadRepliesData(threadId: Int) {
        runBlocking {
            launch(context = Dispatchers.IO) {
                val threadReplies = threadRepository.getThreadReplies(threadId, currentUserId)
                if (threadReplies != null) {
                    _data.update { threadReplies }
                }
            }
        }
    }

    // 3. ---- Thread Replying Reply
    override fun favoriteThreadReplyingReply(isFavorite: Boolean, index: Int) {
        favorite(isFavorite, index)
        runBlocking {
            launch(context = Dispatchers.IO) {
                val threadReplyingReplyId = data.value[index].content.id
                threadRepository.favoriteThreadReplyingReply(
                    threadReplyingReplyId,
                    isFavorite,
                    currentUserId
                )
            }
        }
    }

    override fun retrieveThreadReplyingReplies(threadReplyId: Int) {
        runBlocking {
            launch(context = Dispatchers.IO) {
                val threadReplyingReplies = threadRepository.getThreadReplyingReplies(threadReplyId)
                if (threadReplyingReplies != null) {
                    _data.update { threadReplyingReplies }
                }
            }
        }
    }
}
