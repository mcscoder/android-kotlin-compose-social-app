package com.example.thread.data.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.thread.data.model.favorite.Favorite
import com.example.thread.data.model.user.UserReplies
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserRepliesData(private val userId: Int) {
    private val _data = MutableStateFlow<List<UserReplies>>(emptyList())
    val data: StateFlow<List<UserReplies>> = _data.asStateFlow()

    private val currentUserId = GlobalViewModelProvider.getUserId()
    private val threadRepository = ThreadRepository()

    fun retrieveRepliesData() {
        runBlocking {
            launch(context = Dispatchers.IO) {
                val userReplies = threadRepository.getRepliesByUser(userId)
                if (userReplies != null) {
                    _data.update { userReplies }
                }
            }
        }
    }

    fun favoritePost(isFavorite: Boolean, index: Int) {
        _data.update { userReplies ->
            val updatedUserReplies = userReplies.toMutableList()
            val replies = updatedUserReplies[index]
            val updatedReplies =
                replies.copy(
                    mainThread = replies.mainThread.copy(
                        favorite = Favorite(
                            favoriteCount = replies.mainThread.favorite.favoriteCount + if (isFavorite) 1 else -1,
                            isFavorite = isFavorite
                        )
                    )
                )
            updatedUserReplies[index] = updatedReplies
            updatedUserReplies
        }
        // Sync to the database on the server
        runBlocking {
            launch(context = Dispatchers.IO) {
                val threadId = data.value[index].mainThread.content.id
                threadRepository.favoriteThread(threadId, isFavorite, currentUserId)
            }
        }
    }

    fun favoriteThreadReply(isFavorite: Boolean, threadReplyIndex: Int, index: Int) {
        _data.update { userReplies ->
            val updatedUserReplies = userReplies.toMutableList()

            val updatedThreadReplies = updatedUserReplies[index].threadReplies.toMutableList()
            updatedThreadReplies[threadReplyIndex] = updatedThreadReplies[threadReplyIndex].copy(
                favorite = Favorite(
                    favoriteCount = updatedThreadReplies[threadReplyIndex].favorite.favoriteCount + if (isFavorite) 1 else -1,
                    isFavorite = isFavorite
                )
            )

            updatedUserReplies[index] =
                updatedUserReplies[index].copy(threadReplies = updatedThreadReplies)
            updatedUserReplies
        }

        runBlocking {
            launch(context = Dispatchers.IO) {
                threadRepository.favoriteThreadReply(
                    data.value[index].threadReplies[threadReplyIndex].content.id,
                    isFavorite,
                    currentUserId
                )
            }
        }
    }
}
