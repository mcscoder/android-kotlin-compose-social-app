package com.example.thread.data.viewmodel.threaddata

interface ThreadReplyingReplies : ThreadStateFlow {
    fun favoriteThreadReplyingReply(isFavorite: Boolean, index: Int)
    fun retrieveThreadReplyingReplies(threadReplyId: Int)
}
