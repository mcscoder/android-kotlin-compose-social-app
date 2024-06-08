package com.example.thread.ui.screen.secondary.threaddetails

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.threaddata.ThreadsData

class ThreadDetailsViewModel(val threadsData: ThreadsData, threadIndex: Int) :
    ViewModel() {
    val repliesData = ThreadsData()
    private val mainPostData = threadsData.data.value[threadIndex]

    init {
        getReplies()
    }

    private fun getReplies() {
        repliesData.getReplies(mainPostData.content.threadId)
    }
}
