package com.example.thread.ui.screen.primary.profile

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.data.viewmodel.UserData
import com.example.thread.data.viewmodel.UserRepliesData
import com.example.thread.ui.screen.GlobalViewModelProvider
import com.example.thread.ui.screen.ThreadViewModelProvider
import com.example.thread.ui.screen.ViewModelProviderManager

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
        threadsData.retrieveUserThreadsData(userId)
    }

    fun retrieveUserData() {
        userData.retrieveUserData()
    }

    fun retrieveRepliesData() {
        userRepliesData.retrieveRepliesData()
    }
}
