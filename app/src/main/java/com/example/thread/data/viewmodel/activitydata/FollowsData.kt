package com.example.thread.data.viewmodel.activitydata

import com.example.thread.data.model.user.ActivityFollowResponse
import com.example.thread.data.model.user.UserResponse
import com.example.thread.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowsData {
    private val _data = MutableStateFlow<List<ActivityFollowResponse>>(emptyList())
    val data: StateFlow<List<ActivityFollowResponse>> = _data.asStateFlow()

    private val userRepository = UserRepository()

    fun retrieveUserFollowers(targetUserId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val userFollowers = userRepository.getUserFollowers(targetUserId)
            _data.update { userFollowers }
        }
    }

    fun retrieveUserFollowings(targetUserId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val userFollowings = userRepository.getUserFollowings(targetUserId)
            _data.update { userFollowings }
        }
    }

    fun onFollowUser(targetUserId: Int, onResponse: () -> Unit = {}) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.followUser(targetUserId)
            onResponse()
        }
    }
}
