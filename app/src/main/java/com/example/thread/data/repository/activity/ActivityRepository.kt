package com.example.thread.data.repository.activity

import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.activity.ReplyActivity
import com.example.thread.data.model.user.User
import com.example.thread.data.repository.user.UserRepository

class ActivityRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
) : ReplyActivityRepository {
    override fun getReplyActivities(): List<ReplyActivity>? {
        return apiService.getReplyActivities().execute().body()
    }
}
