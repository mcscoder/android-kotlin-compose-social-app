package com.example.thread.data.viewmodel.activitydata

import com.example.thread.data.model.activity.ReplyActivity
import com.example.thread.data.repository.activity.ActivityRepository
import com.example.thread.data.repository.activity.ReplyActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReplyActivitiesData {
    private val _data = MutableStateFlow<List<ReplyActivity>>(emptyList())
    val data: StateFlow<List<ReplyActivity>> = _data.asStateFlow()

    private val activityRepository: ReplyActivityRepository = ActivityRepository()

    fun retrieveReplyActivities() {
        CoroutineScope(Dispatchers.IO).launch {
            val replyActivities = activityRepository.getReplyActivities()
            if (replyActivities != null) {
                _data.update { replyActivities }
            }
        }
    }
}
