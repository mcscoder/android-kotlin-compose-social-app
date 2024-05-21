package com.example.thread.ui.screen.primary.home

import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.threaddata.MainThreads
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import com.example.thread.ui.screen.ThreadViewModelProvider
import com.example.thread.ui.screen.ViewModelProviderManager

object HomeViewModelProvider : ThreadViewModelProvider {
    private var instance: HomeViewModel? = null

    init {
        ViewModelProviderManager.addProvider(this)
    }

    fun getInstance(): HomeViewModel {
        synchronized(lock = this) {
            if (instance == null) {
                instance = HomeViewModel()
            }
            return instance!!
        }
    }

    override fun clear() {
        instance = null
    }
}

class HomeViewModel() : ViewModel() {
    val threadsData: MainThreads = ThreadsData()

    init {
        threadsData.retrieveRandomThreadData(10)
    }
}
