package com.example.thread.data.viewmodel

import android.util.Log
import com.example.thread.data.model.thread.MainThreadWithRepliesResponse
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainThreadWithRepliesData(private val targetUserId: Int) {
    val mainThreadsData: ThreadsData = ThreadsData()
    val repliesData: MutableList<ThreadsData> = mutableListOf()

    private val threadRepository = ThreadRepository()

    fun retrieveRepliesData() {
        CoroutineScope(Dispatchers.IO).launch {
            val mainThreadWithRepliesResponses =
                threadRepository.getMainThreadWithReplies(targetUserId)

            mainThreadsData.setThreadResponsesData(mainThreadWithRepliesResponses.map { it.main })
            repliesData.addAll(mainThreadWithRepliesResponses.map { ThreadsData(it.replies) })
        }
    }

    fun favoritePost(isFavorited: Boolean, elementIndex: Int) {
        mainThreadsData.favoriteThread(isFavorited, elementIndex)
    }

    fun favoriteThreadReply(isFavorited: Boolean, elementIndex: Int, replyElementIndex: Int) {
        repliesData[elementIndex].favoriteThread(isFavorited, replyElementIndex)
    }
}
