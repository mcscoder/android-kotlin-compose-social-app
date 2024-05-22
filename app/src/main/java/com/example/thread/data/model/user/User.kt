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

    val following: Boolean = false,

    val followers: Int = 0,
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
