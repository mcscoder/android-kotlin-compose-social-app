package com.example.thread.data.model.user

import com.example.thread.data.model.thread.ThreadResponse
import com.google.gson.annotations.SerializedName

data class User(
    val userId: Int = 0,
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val imageUrl: String = "",
)

data class FollowOverview (
    val isFollowing: Boolean,
    val count: Int,
)

data class UserOverview(
    val follow: FollowOverview,
)

data class UserResponse(
    val user: User,
    val overview: UserOverview
)

data class UserReplies(
    @field:SerializedName("mainThread")
    val mainThread: ThreadResponse,

    @field:SerializedName("threadReplies")
    val threadReplies: List<ThreadResponse>,
)

data class UserLoginRequest(
    val username: String,
    val password: String,
)

data class LoginResponse(
    val user: User? = null,
    val message: String? = null,
)
