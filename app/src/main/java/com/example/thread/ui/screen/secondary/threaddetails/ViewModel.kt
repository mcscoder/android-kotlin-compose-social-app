package com.example.thread.ui.screen.secondary.threaddetails

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.ThreadReplies
import com.example.thread.data.viewmodel.ThreadsData

class ThreadDetailsViewModel(private val threadId: Int) : ViewModel() {
    val threadRepliesData: ThreadReplies = ThreadsData()

    init {
        retrieveThreadRepliesData()
    }

    fun favoriteThreadReply(isFavorite: Boolean, index: Int) {
        threadRepliesData.favoriteThreadReply(isFavorite, index)
    }

    private fun retrieveThreadRepliesData() {
        threadRepliesData.retrieveThreadRepliesData(threadId)
    }
}
