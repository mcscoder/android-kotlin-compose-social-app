package com.example.thread.data.repository.activity

import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.activity.ReplyActivity
import com.example.thread.data.model.user.User

class ActivityRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
): ReplyActivityRepository, FollowActivityRepository {
    override fun getReplyActivities(): List<ReplyActivity>? {
        return apiService.getReplyActivities().execute().body()
    }

    override fun getFollowActivities(): List<FollowActivity>? {
        return apiService.getFollowActivities().execute().body()
    }
}
