package com.example.thread.data.repository.thread

import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.response.ResponseMessage
import com.example.thread.data.model.thread.Thread
import com.example.thread.data.model.thread.ThreadRequest
import com.example.thread.data.model.user.UserReplies
import com.example.thread.data.repository.resource.ResourceRepository
import com.example.thread.ui.screen.GlobalViewModelProvider

class ThreadRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
    private val resourceRepository: ResourceRepository = ResourceRepository(),
) {
    // 1. Get random Threads
    fun getRandomThreads(count: Int, userId: Int): List<Thread>? {
        return apiService.getRandomThreads(count, userId).execute().body()
    }

    // 2. Get Thread replies
    fun getThreadReplies(mainThreadId: Int, userId: Int): List<Thread>? {
        return apiService.getThreadReplies(mainThreadId, userId).execute().body()
    }

    // 3. Favorite a Thread
    fun favoriteThread(
        threadId: Int,
        isFavorite: Boolean,
        userId: Int,
    ): ResponseMessage? {
        return apiService.favoriteThread(threadId, if (isFavorite) true else null, userId).execute()
            .body()
    }

    // 4. Favorite a Thread reply
    fun favoriteThreadReply(
        threadId: Int,
        isFavorite: Boolean,
        userId: Int,
    ): ResponseMessage? {
        return apiService.favoriteThreadReply(threadId, if (isFavorite) true else null, userId)
            .execute().body()
    }

    // 5. Post a Thread
    fun postThread(text: String, imageFiles: List<ByteArray>, userId: Int): ResponseMessage? {
        var newThreadRequest = ThreadRequest(text = text, userId = userId)
        if (imageFiles.isNotEmpty()) {
            val imageIds = resourceRepository.uploadImages(imageFiles)
            newThreadRequest = newThreadRequest.copy(imageIds = imageIds)
        }
        return apiService.postThread(newThreadRequest).execute().body()
    }

    // 6. Post a Thread reply
    fun postThreadReply(
        text: String,
        imageFiles: List<ByteArray>,
        mainThreadId: Int,
        userId: Int,
    ): ResponseMessage? {
        var newThreadRequest =
            ThreadRequest(text = text, mainThreadId = mainThreadId, userId = userId)
        if (imageFiles.isNotEmpty()) {
            val imageIds = resourceRepository.uploadImages(imageFiles)
            newThreadRequest = newThreadRequest.copy(imageIds = imageIds)
        }
        return apiService.postThreadReply(newThreadRequest).execute().body()
    }

    // 7. Get all Threads by User
    fun getThreadsByUser(
        userId: Int = GlobalViewModelProvider.getInstance().getUser().id,
    ): List<Thread>? {
        return apiService.getThreadsByUser(userId).execute().body()
    }

    // 8. Get all Replies by User
    fun getRepliesByUser(
        userId: Int = GlobalViewModelProvider.getInstance().getUser().id,
    ): List<UserReplies>? {
        return apiService.getRepliesByUser(userId).execute().body()
    }

    // 9. Post a Thread replying reply
    fun postThreadReplyingReply(
        text: String,
        imageFiles: List<ByteArray>,
        threadReplyId: Int,
        userId: Int,
    ): ResponseMessage? {
        var newThreadRequest =
            ThreadRequest(text = text, threadReplyId = threadReplyId, userId = userId)
        if (imageFiles.isNotEmpty()) {
            val imageIds = resourceRepository.uploadImages(imageFiles)
            newThreadRequest = newThreadRequest.copy(imageIds = imageIds)
        }
        return apiService.postThreadReplyingReply(newThreadRequest).execute().body()
    }

    // 10. Get Thread Replying Replies
    fun getThreadReplyingReplies(threadReplyId: Int): List<Thread>? {
        return apiService.getThreadReplyingReplies(threadReplyId).execute().body()
    }

    // 11. Favorite thread replying reply
    fun favoriteThreadReplyingReply(
        threadId: Int,
        isFavorite: Boolean,
        userId: Int,
    ): ResponseMessage? {
        return apiService.favoriteThreadReplyingReply(
            threadId,
            if (isFavorite) true else null,
            userId
        ).execute().body()
    }
}
