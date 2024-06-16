package com.example.thread.ui.screen.secondary.threaddetails

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.threaddata.ThreadsData

class ThreadDetailsViewModel(val threadsData: ThreadsData, val threadIndex: Int) :
    ViewModel() {
    val repliesData = ThreadsData()
    private val mainPostData = threadsData.data.value[threadIndex]

    fun getReplies() {
        repliesData.getReplies(mainPostData.content.threadId)
    }
}
