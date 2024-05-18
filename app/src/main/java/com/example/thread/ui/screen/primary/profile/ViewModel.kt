package com.example.thread.ui.screen.primary.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.data.model.user.User
import com.example.thread.data.model.user.UserReplies
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.data.repository.user.UserRepository
import com.example.thread.data.viewmodel.ThreadsData
import com.example.thread.data.viewmodel.UserData
import com.example.thread.data.viewmodel.UserRepliesData
import com.example.thread.ui.screen.GlobalViewModelProvider
import com.example.thread.ui.screen.ThreadViewModelProvider
import com.example.thread.ui.screen.ViewModelProviderManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object ProfileViewModelProvider : ThreadViewModelProvider {
    private var myProfileInstance: ProfileViewModel? = null
    private var otherProfileInstance: ProfileViewModel? = null

    init {
        ViewModelProviderManager.addProvider(this)
    }

    fun getInstance(userId: Int): ProfileViewModel {
        synchronized(lock = this) {
            if (userId == GlobalViewModelProvider.getInstance().getUser().id) {
                if (myProfileInstance == null) {
                    myProfileInstance = ProfileViewModel(userId)
                }
                return myProfileInstance!!
            }

            if (otherProfileInstance == null) {
                otherProfileInstance = ProfileViewModel(userId)
            } else if (otherProfileInstance?.userId != userId) {
                otherProfileInstance = ProfileViewModel(userId)
            }

            return otherProfileInstance!!
        }
    }

    fun getOtherProfileInstanceOrNull(): ProfileViewModel? {
        return otherProfileInstance
    }

    fun getMyProfileInstanceOrNull(): ProfileViewModel? {
        return myProfileInstance
    }

    override fun clear() {
        otherProfileInstance = null
        myProfileInstance = null
    }
}

class ProfileViewModel(val userId: Int) : ViewModel() {
    val userData = UserData(userId)
    val threadsData = ThreadsData()
    val userRepliesData = UserRepliesData(userId)

    init {
        retrieveUserData()
        retrieveThreadData()
    }

    fun retrieveThreadData() {
        threadsData.retrieveThreadData(userId)
    }

    fun retrieveUserData() {
        userData.retrieveUserData()
    }

    fun retrieveRepliesData() {
        userRepliesData.retrieveRepliesData()
    }
}
