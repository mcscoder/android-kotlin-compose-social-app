package com.example.thread.data.viewmodel.activitydata

import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.repository.user.UserRepository
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowsData {
    private val _data = MutableStateFlow<List<FollowActivity>>(emptyList())
    val data: StateFlow<List<FollowActivity>> = _data.asStateFlow()

    private val userRepository = UserRepository()
    private val currentUserId = GlobalViewModelProvider.getCurrentUserId()

    fun retrieveFollowersData(targetUserId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val followActivities = userRepository.getFollowers(targetUserId)
            if (followActivities != null) {
                _data.update { followActivities }
            }
        }
    }

    fun retrieveFollowingsData(targetUserId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val followActivities = userRepository.getFollowings(targetUserId)
            if (followActivities != null) {
                _data.update { followActivities }
            }
        }
    }

    fun onFollowUser(targetUserId: Int) {
        userRepository.followUser(targetUserId) {
            retrieveFollowersData(currentUserId)
        }
    }
}
