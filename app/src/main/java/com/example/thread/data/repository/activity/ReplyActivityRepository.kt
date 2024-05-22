package com.example.thread.data.repository.activity

import com.example.thread.data.model.activity.ReplyActivity

interface ReplyActivityRepository {
    fun getReplyActivities(): List<ReplyActivity>?
}
