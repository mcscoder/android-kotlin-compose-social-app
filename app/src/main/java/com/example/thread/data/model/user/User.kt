package com.example.thread.data.model.user

import com.example.thread.data.model.thread.Thread
import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("username")
    val username: String = "",

    @field:SerializedName("firstName")
    val firstName: String = "",

    @field:SerializedName("lastName")
    val lastName: String = "",

    @field:SerializedName("avatarURL")
    val avatarURL: String? = null,
)

data class OtherUser(
    val user: User,
    val status: OtherUserStatus,
)

data class OtherUserStatus(
    val followStatus: FollowStatus,
)

data class FollowStatus(
    val followed: Boolean, // True if others are following you, otherwise False
    val following: Boolean, // True if you are following others, otherwise False
)

data class UserReplies(
    @field:SerializedName("mainThread")
    val mainThread: Thread,

    @field:SerializedName("threadReplies")
    val threadReplies: List<Thread>,
)

data class LoginRequest(
    val username: String,
    val password: String,
)

data class LoginResponse(
    val user: User? = null,
    val message: String? = null,
)
