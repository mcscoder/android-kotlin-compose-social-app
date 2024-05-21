package com.example.thread.ui.screen.primary.activity

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.activitydata.ReplyActivitiesData

class ActivityViewModel : ViewModel() {
    val replies = ReplyActivitiesData()
}
