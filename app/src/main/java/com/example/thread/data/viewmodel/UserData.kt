package com.example.thread.data.viewmodel

import com.example.thread.data.model.user.UpdateProfileRequest
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

    val editedBio = TextData()
    val editedFirstName = TextData()
    val editedLastName = TextData()

    private val userRepository = UserRepository()

    fun setDefaultEditedBio() {
        editedBio.setText(data.value?.user?.bio ?: "")
    }

    fun setDefaultEditedFirstName() {
        editedFirstName.setText(data.value?.user?.firstName ?: "")
    }

    fun setDefaultEditedLastName() {
        editedLastName.setText(data.value?.user?.lastName ?: "")
    }

    fun updateName(newFirstName: String, newLastName: String) {
        _data.update { data ->
            var updatedData = data
            if (updatedData != null) {
                var updatedUser = updatedData.user

                updatedUser = updatedUser.copy(firstName = newFirstName, lastName = newLastName)
                updatedData = updatedData.copy(user = updatedUser)
            }

            updatedData
        }
    }

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

    fun updateProfile() {
        updateName(editedFirstName.value.text, editedLastName.value.text)
        updateBio(editedBio.value.text)
        CoroutineScope(Dispatchers.IO).launch {
            val requestBody = UpdateProfileRequest(
                firstName = editedFirstName.value.text,
                lastName = editedLastName.value.text,
                bio = editedBio.value.text
            )
            userRepository.updateUserProfile(requestBody)
        }
    }

    // Return true if there are any changes from these fields
    fun isProfileInfoChanged(): Boolean {
        val user = data.value!!.user
        return user.firstName != editedFirstName.value.text ||
                user.lastName != editedLastName.value.text ||
                user.bio != editedBio.value.text
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
