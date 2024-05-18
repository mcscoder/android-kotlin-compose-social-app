package com.example.thread.ui.screen

interface ThreadViewModelProvider {
    fun clear()
}

object ViewModelProviderManager : ThreadViewModelProvider {
    private val viewModels = mutableListOf<ThreadViewModelProvider>()

    fun addProvider(provider: ThreadViewModelProvider) {
        viewModels.add(provider)
    }

    override fun clear() {
        viewModels.forEach { it.clear() }
    }
}
