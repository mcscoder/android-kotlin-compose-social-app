package com.example.thread.ui.screen.primary.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.activitydata.FollowActivitiesData
import com.example.thread.data.viewmodel.activitydata.ReplyActivitiesData
import com.example.thread.ui.screen.ThreadViewModelProvider

object ActivityViewModelProvider : ThreadViewModelProvider {
    private var instance: ActivityViewModel? = null
    var currentPageIndex = 0

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
    val replies = ReplyActivitiesData()
    val follows = FollowActivitiesData()
}
