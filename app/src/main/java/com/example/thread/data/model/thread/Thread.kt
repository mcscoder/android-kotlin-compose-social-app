package com.example.thread.data.model.thread

import com.example.thread.data.model.common.DateTime
import com.example.thread.data.model.favorite.Favorite
import com.example.thread.data.model.user.User
import com.google.gson.annotations.SerializedName

data class Thread(
    // if variable name is match to json key we don't need to specified @field:SerializedName("[key]")
    // below is example
    // @field:SerializedName("content")
    val content: ThreadEntity,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("favorite")
    val favorite: Favorite,

    @field:SerializedName("replyCount")
    val replyCount: Int,

    @field:SerializedName("imageURLs")
    val imageURLs: List<String> = emptyList(),

    val dateTime: DateTime
)

data class ThreadRequest(
    @field:SerializedName("text")
    val text: String?,

    @field:SerializedName("mainThreadId")
    val mainThreadId: Int? = null,

    @field:SerializedName("threadReplyId")
    val threadReplyId: Int? = null,

    @field:SerializedName("userId")
    val userId: Int,

    @field:SerializedName("imageIds")
    val imageIds: List<Int>? = null,
)

data class ThreadEntity(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("mainThreadId")
    val mainThreadId: Int? = null,

    val threadReplyId: Int? = null,

    @field:SerializedName("favorite")
    val favorite: Int = 0,

    @field:SerializedName("userId")
    val userId: Int,
)
