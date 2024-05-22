package com.example.thread.data.model.activity

import com.example.thread.data.model.common.DateTime
import com.example.thread.data.model.user.User

data class FollowActivity(
    val user: User,
    val dateTime: DateTime
)
