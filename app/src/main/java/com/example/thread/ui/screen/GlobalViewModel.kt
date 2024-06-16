package com.example.thread.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.data.model.user.User
import com.example.thread.data.repository.user.UserRepository
import com.example.thread.data.viewmodel.threaddata.ThreadData
import com.example.thread.data.viewmodel.threaddata.ThreadsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object GlobalViewModelProvider : ThreadViewModelProvider {
    private var instance: GlobalViewModel? = null

    val displayNewThread = mutableStateOf(false)
    val displayReplyThread = mutableStateOf(false)
    val threadData = mutableStateOf<ThreadData?>(null)

    init {
        ViewModelProviderManager.addProvider(this)
    }

    fun displayReplyThreadScreen(threadsData: ThreadsData, threadIndex: Int) {
        threadData.value = ThreadData(threadsData, threadIndex)
        displayReplyThread.value = true
    }

    fun init(user: User): GlobalViewModel {
        synchronized(this) {
            if (instance == null) {
                instance = GlobalViewModel()
            }
            instance!!.init(user)
            return instance!!
        }
    }

    fun init(userId: Int): GlobalViewModel {
        synchronized(this) {
            if (instance == null) {
                instance = GlobalViewModel()
                instance!!.init(userId)
            }
            return instance!!
        }
    }

    fun getInstance(): GlobalViewModel {
        synchronized(lock = this) {
            return instance!!
        }
    }

    fun getCurrentUserId(): Int {
        return getInstance().getUser().userId
    }

    override fun clear() {
        instance = null
    }
}

class GlobalViewModel : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user.asStateFlow()

    fun init(user: User) {
        _user.update { user }
    }

    fun init(userId: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val user = UserRepository().getUser(userId)
            if (user != null) {
                // delay(2000)
                _user.update { user.user }
            }
        }
    }

    fun getUser(): User {
        return user.value
    }
}
