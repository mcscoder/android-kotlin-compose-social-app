package com.example.thread.data.repository.thread

import android.util.Log
import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.response.ResponseMessage
import com.example.thread.data.model.thread.MainThreadWithRepliesResponse
import com.example.thread.data.model.thread.ThreadRequest
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.data.model.user.UserReplies
import com.example.thread.data.repository.resource.ResourceRepository
import com.example.thread.ui.screen.GlobalViewModelProvider

class ThreadRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
    private val resourceRepository: ResourceRepository = ResourceRepository(),
) {
    // 2.1. Get Thread by `threadId`
    suspend fun getThread(threadId: Int): ThreadResponse? {
        return apiService.getThread(threadId).body()
    }

    // 2.2. Get a random list of Thread posts
    fun getRandomThreads(): List<ThreadResponse>? {
        return apiService.getRandomThreads().execute().body()
    }

    // 2.3. Post a Thread
    fun postThread(text: String, type: Int, imageFiles: List<ByteArray>, mainId: Int? = null) {
        var requestBody = ThreadRequest(text, type, emptyList(), mainId)
        if (imageFiles.isNotEmpty()) {
            val imageUrls = resourceRepository.uploadImages(imageFiles)
            requestBody = requestBody.copy(imageUrls = imageUrls)
        }
        apiService.postThread(requestBody).execute()
    }

    // 2.4. Favorite or unfavorite a Thread
    fun favoriteThread(threadId: Int, isFavorited: Boolean) {
        apiService.favoriteThread(threadId, if (isFavorited) 1 else 0).execute()
    }

    // 2.5. Get a list of comments|replies by `mainId`
    fun getReplies(mainId: Int): List<ThreadResponse> {
        return apiService.getReplies(mainId).execute().body()!!
    }

    // 2.6. Get a list of Posts|Comments|Replies by `userId`
    fun getThreadsByUserId(targetUserId: Int, type: Int): List<ThreadResponse> {
        return apiService.getThreadsByUserId(targetUserId, type).execute().body()!!
    }

    // 2.7. Get all Replies by `userId` included Main Thread
    fun getMainThreadWithReplies(targetUserId: Int): List<MainThreadWithRepliesResponse> {
        return apiService.getMainThreadWithReplies(targetUserId).execute().body()!!
    }

    // 2.8. Get all comments that comments to current user's post
    suspend fun getActivityReplies(): List<ThreadResponse> {
        return apiService.getActivityReplies().body()!!
    }

    // ---------------------------------------------------------- below is for testing
    // 2. Get Thread replies (remove)
    fun getThreadReplies(mainThreadId: Int, userId: Int): List<ThreadResponse>? {
        return apiService.getReplies(mainThreadId, userId).execute().body()
    }

    // 3. Favorite a Thread
    // fun favoriteThread(
    //     threadId: Int,
    //     isFavorite: Boolean,
    //     userId: Int,
    // ): ResponseMessage? {
    //     return apiService.favoriteThread(threadId, if (isFavorite) true else null, userId).execute()
    //         .body()
    // }

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
    // fun postThread(text: String, imageFiles: List<ByteArray>, userId: Int): ResponseMessage? {
    //     // var newThreadRequest = ThreadRequest(text = text, userId = userId)
    //     // if (imageFiles.isNotEmpty()) {
    //     //     val imageIds = resourceRepository.uploadImages(imageFiles)
    //     //     newThreadRequest = newThreadRequest.copy(imageIds = imageIds)
    //     // }
    //     // return apiService.postThread(newThreadRequest).execute().body()
    //     return null
    // }

    // 6. Post a Thread reply
    fun postThreadReply(
        text: String,
        imageFiles: List<ByteArray>,
        mainThreadId: Int,
        userId: Int,
    ): ResponseMessage? {
        // var newThreadRequest =
        //     ThreadRequest(text = text, mainThreadId = mainThreadId, userId = userId)
        // if (imageFiles.isNotEmpty()) {
        //     val imageIds = resourceRepository.uploadImages(imageFiles)
        //     newThreadRequest = newThreadRequest.copy(imageIds = imageIds)
        // }
        // return apiService.postThreadReply(newThreadRequest).execute().body()
        return null
    }

    // 7. Get all Threads by User
    fun getThreadsByUser(
        userId: Int = GlobalViewModelProvider.getInstance().getUser().userId,
    ): List<ThreadResponse>? {
        return apiService.getThreadsByUser(userId).execute().body()
    }

    // 8. Get all Replies by User
    fun getRepliesByUser(
        userId: Int = GlobalViewModelProvider.getInstance().getUser().userId,
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
        // var newThreadRequest =
        //     ThreadRequest(text = text, threadReplyId = threadReplyId, userId = userId)
        // if (imageFiles.isNotEmpty()) {
        //     val imageIds = resourceRepository.uploadImages(imageFiles)
        //     newThreadRequest = newThreadRequest.copy(imageIds = imageIds)
        // }
        // return apiService.postThreadReplyingReply(newThreadRequest).execute().body()
        return null
    }

    // 10. Get Thread Replying Replies
    fun getThreadReplyingReplies(threadReplyId: Int): List<ThreadResponse>? {
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
