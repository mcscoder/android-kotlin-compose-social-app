package com.example.thread.data.viewmodel.threaddata

interface ThreadReplies : ThreadStateFlow {
    fun favoriteThreadReply(isFavorite: Boolean, index: Int)
    fun retrieveThreadRepliesData(threadId: Int)

    // fun retrieveThreadReplyById(threadReplyId: Int)
}
