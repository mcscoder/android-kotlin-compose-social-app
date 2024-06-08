package com.example.thread.data.viewmodel.threaddata

import com.example.thread.data.model.thread.FavoriteOverviewResponse
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThreadsData {
    private val _data = MutableStateFlow<List<ThreadResponse>>(emptyList())
    val data: StateFlow<List<ThreadResponse>> = _data.asStateFlow()
    private val currentUserId = GlobalViewModelProvider.getInstance().getUser().userId

    private val threadRepository = ThreadRepository()

    private fun favorite(isFavorited: Boolean, index: Int) {
        _data.update { data ->
            val updatedData = data.toMutableList()
            updatedData[index] = updatedData[index].copy(
                overview = updatedData[index].overview.copy(
                    favorite = FavoriteOverviewResponse(
                        count = updatedData[index]
                            .overview
                            .favorite
                            .count + if (isFavorited) 1 else -1,
                        isFavorited = isFavorited
                    )
                ),
            )
            updatedData
        }
    }

    fun getRandomThreads(reload: Boolean = false) {
        CoroutineScope(Dispatchers.IO).launch {
            val threadResponses = threadRepository.getRandomThreads()
            if (threadResponses != null) {
                _data.update { threads ->
                    if (reload) {
                        threadResponses
                    } else {
                        val updatedThreads = threads.toMutableList()
                        updatedThreads.addAll(threadResponses)
                        updatedThreads
                    }
                }
            }
        }
    }

    fun favoriteThread(isFavorited: Boolean, index: Int) {
        favorite(isFavorited, index)
        CoroutineScope(Dispatchers.IO).launch {
            threadRepository.favoriteThread(data.value[index].content.threadId, isFavorited)
        }
    }

    fun getReplies(mainId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val replies = threadRepository.getReplies(mainId)
            _data.update { replies }
        }
    }
}
