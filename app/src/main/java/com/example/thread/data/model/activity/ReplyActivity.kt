package com.example.thread.data.model.activity

import com.example.thread.data.model.thread.ThreadResponse

data class ReplyActivity(
    val reply: ThreadResponse,
    val type: Int
)
