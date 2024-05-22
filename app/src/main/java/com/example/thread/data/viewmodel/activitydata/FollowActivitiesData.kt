package com.example.thread.data.viewmodel.activitydata

import android.util.Log
import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.user.User
import com.example.thread.data.repository.activity.ActivityRepository
import com.example.thread.data.repository.activity.FollowActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowActivitiesData {
    private val _data = MutableStateFlow<List<FollowActivity>>(emptyList())
    val data: StateFlow<List<FollowActivity>> = _data.asStateFlow()

    private val activityRepository: FollowActivityRepository = ActivityRepository()

    fun retrieveFollowActivities() {
        CoroutineScope(Dispatchers.IO).launch {
            val followActivities = activityRepository.getFollowActivities()
            if (followActivities != null) {
                _data.update { followActivities }
            }
        }
    }
}
