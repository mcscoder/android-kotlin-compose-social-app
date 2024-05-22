package com.example.thread.ui.screen.secondary.followerlist

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.activitydata.FollowsData

class FollowListViewModel: ViewModel() {
    val followers = FollowsData()
    val followings = FollowsData()
}
