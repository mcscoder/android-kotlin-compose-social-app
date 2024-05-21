package com.example.thread.data.repository.activity

import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.activity.ReplyActivity

class ActivityRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
) {
    fun getReplyActivities(): List<ReplyActivity>? {
        return apiService.getRepliesActivity().execute().body()
    }
}
