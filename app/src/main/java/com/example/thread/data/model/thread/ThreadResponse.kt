package com.example.thread.data.model.thread

import com.example.thread.data.model.common.DateTime
import com.example.thread.data.model.user.UserResponse

data class Thread(
    val threadId: Int,
    val text: String,
    val imageUrls: List<String>,
    val type: Int,
    val dateTime: DateTime,
)

data class FavoriteOverviewResponse(
    val count: Int,
    val isFavorited: Boolean,
)

data class ReplyOverviewResponse(
    val count: Int,
)

data class ThreadOverviewResponse(
    val favorite: FavoriteOverviewResponse,
    val reply: ReplyOverviewResponse,
)

data class ThreadResponse(
    val content: Thread,
    val user: UserResponse,
    val overview: ThreadOverviewResponse,
)

enum class ThreadType {
    POST,
    COMMENT,
    REPLY,
}

data class ThreadRequest(
    val text: String,
    val type: Int,
    val imageUrls: List<String>,
    val mainId: Int? = null,
)

data class MainThreadWithRepliesResponse(
    val main: ThreadResponse,
    val replies: List<ThreadResponse>
)
