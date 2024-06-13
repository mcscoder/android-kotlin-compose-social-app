package com.example.thread.ui.screen.primary.profile

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.thread.data.model.thread.ThreadType
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.data.viewmodel.UserData
import com.example.thread.data.viewmodel.MainThreadWithRepliesData
import com.example.thread.data.viewmodel.TextData
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
            // userId === Current User Id
            // Current user profile case
            if (userId == GlobalViewModelProvider.getCurrentUserId()) {
                if (myProfileInstance == null) {
                    myProfileInstance = ProfileViewModel(userId)
                }
                return myProfileInstance!!
            }

            // Other users profile case
            if (otherProfileInstance == null) {
                otherProfileInstance = ProfileViewModel(userId)
            } else if (otherProfileInstance?.targetUserId != userId) {
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

class ProfileViewModel(val targetUserId: Int) : ViewModel() {
    val userData = UserData(targetUserId)
    val threadsData = ThreadsData()
    val mainThreadWithReplies = MainThreadWithRepliesData(targetUserId)
    var currentPageIndex by mutableIntStateOf(0)

    init {
        userData.retrieveUserData()
        threadsData.getThreadsByUserId(targetUserId, ThreadType.POST.ordinal)
    }
}
