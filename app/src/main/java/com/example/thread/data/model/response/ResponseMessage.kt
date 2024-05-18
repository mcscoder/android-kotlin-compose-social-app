package com.example.thread.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("message")
    val message: String = "Response message",
)
