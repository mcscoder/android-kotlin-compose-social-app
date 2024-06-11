package com.example.thread.ui.screen.primary.search

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.threaddata.ThreadsData

class SearchResultsViewModel(searchText: String): ViewModel() {
    val threadsData = ThreadsData()

    init {
        threadsData.getThreadsByText(searchText)
    }
}
