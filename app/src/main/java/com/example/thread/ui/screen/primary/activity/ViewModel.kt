package com.example.thread.ui.screen.primary.activity

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.activitydata.FollowsData
import com.example.thread.data.viewmodel.activitydata.ReplyActivitiesData
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.ui.screen.GlobalViewModelProvider
import com.example.thread.ui.screen.ThreadViewModelProvider
import com.example.thread.ui.screen.ViewModelProviderManager

object ActivityViewModelProvider : ThreadViewModelProvider {
    private var instance: ActivityViewModel? = null
    var currentPageIndex = 0

    init {
        ViewModelProviderManager.addProvider(this)
    }

    fun getInstance(): ActivityViewModel {
        synchronized(this) {
            if (instance == null) {
                instance = ActivityViewModel()
            }
            return instance!!
        }
    }

    override fun clear() {
        instance = null
        currentPageIndex = 0
    }
}

class ActivityViewModel : ViewModel() {
    val replies = ThreadsData()
    val follows = FollowsData()

    fun retrieveFollowersData(targetUserId: Int = GlobalViewModelProvider.getCurrentUserId()) {
        follows.retrieveUserFollowers(targetUserId)
    }
}
