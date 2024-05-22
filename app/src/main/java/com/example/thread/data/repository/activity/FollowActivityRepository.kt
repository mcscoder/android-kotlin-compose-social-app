package com.example.thread.data.repository.activity

import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.user.User

interface FollowActivityRepository {
    fun getFollowActivities(): List<FollowActivity>?
}
