package com.example.thread.data.model.user

import com.example.thread.data.model.common.DateTime
import com.example.thread.data.model.thread.ThreadResponse
import com.google.gson.annotations.SerializedName

data class User(
    val userId: Int = 0,
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val bio: String? = null,
    val imageUrl: String = "",
)

data class FollowOverview(
    val isFollowing: Boolean,
    val count: Int,
)

data class UserOverview(
    val follow: FollowOverview,
)

data class UserResponse(
    val user: User,
    val overview: UserOverview,
)

data class ActivityFollowResponse(
    val user: UserResponse,
    val dateTime: DateTime,
)

data class UserReplies(
    @field:SerializedName("mainThread")
    val mainThread: ThreadResponse,

    @field:SerializedName("threadReplies")
    val threadReplies: List<ThreadResponse>,
)

data class UserLoginRequest(
    val email: String,
    val password: String,
)

data class UserRegisterRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
