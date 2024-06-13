package com.example.thread.data.viewmodel

import com.example.thread.data.model.user.UserResponse
import com.example.thread.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserData(private val userId: Int) {
    private val _data = MutableStateFlow<UserResponse?>(null)
    val data: StateFlow<UserResponse?> = _data.asStateFlow()

    private val userRepository = UserRepository()

    fun updateBio(newBio: String) {
        _data.update { data ->
            var updatedData = data
            if (updatedData != null) {
                var updatedUser = updatedData.user

                updatedUser = updatedUser.copy(bio = newBio)
                updatedData = updatedData.copy(user = updatedUser)
            }
            updatedData
        }
    }

    fun retrieveUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUser(targetUserId = userId)
            _data.update { user }
        }
    }

    fun onFollowUser() {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.followUser(userId)
            retrieveUserData()
        }
    }
}
