package com.example.thread.data.viewmodel

import com.example.thread.data.model.user.User
import com.example.thread.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserData(private val userId: Int) {
    private val _data = MutableStateFlow<User?>(null)
    val data: StateFlow<User?> = _data.asStateFlow()

    private val userRepository = UserRepository()

    fun retrieveUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUser(userId = userId)
            _data.update { user }
        }
    }
}
