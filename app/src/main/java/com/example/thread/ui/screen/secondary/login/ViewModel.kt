package com.example.thread.ui.screen.secondary.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.data.model.user.UserLoginRequest
import com.example.thread.data.model.user.UserRegisterRequest
import com.example.thread.data.repository.user.UserRepository
import com.example.thread.ui.screen.ThreadViewModelProvider
import com.example.thread.ui.screen.ViewModelProviderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object LoginViewModelProvider : ThreadViewModelProvider {
    private var instance: LoginViewModel? = null

    init {
        ViewModelProviderManager.addProvider(this)
    }

    fun getInstance(): LoginViewModel {
        synchronized(this) {
            if (instance == null) {
                instance = LoginViewModel()
            }
            return instance!!
        }
    }

    override fun clear() {
        instance = null
    }
}

class LoginViewModel(private val userRepository: UserRepository = UserRepository()) : ViewModel() {
    var username by mutableStateOf(TextFieldValue())
        private set

    var password by mutableStateOf(TextFieldValue())
        private set

    fun updateUsername(newUsername: TextFieldValue) {
        username = newUsername
    }

    fun updatePassword(newPassword: TextFieldValue) {
        password = newPassword
    }

    fun loginSubmit(
        onResponse: CoroutineScope.(userId: Int?) -> Unit = {},
    ) {
        if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
            viewModelScope.launch(context = Dispatchers.IO) {
                val loginRequest = UserLoginRequest(username.text, password.text)
                val userId = userRepository.loginAuthentication(loginRequest)
                onResponse(userId)
            }
        }

        // Dispatchers.IO for a network request
    }
}
