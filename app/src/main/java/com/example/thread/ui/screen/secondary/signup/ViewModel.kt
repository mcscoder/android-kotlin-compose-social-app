package com.example.thread.ui.screen.secondary.signup

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.data.model.user.UserRegisterRequest
import com.example.thread.data.repository.resource.ResourceRepository
import com.example.thread.data.repository.user.UserRepository
import com.example.thread.data.viewmodel.MessageData
import com.example.thread.data.viewmodel.TextData
import com.example.thread.ui.screen.ThreadViewModelProvider
import com.example.thread.ui.screen.ViewModelProviderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SignUpViewModelProvider : ThreadViewModelProvider {
    private var instance: SignUpViewModel? = null

    init {
        ViewModelProviderManager.addProvider(this)
    }

    fun getInstance(): SignUpViewModel {
        synchronized(this) {
            if (instance == null) {
                instance = SignUpViewModel()
            }
            return instance!!
        }
    }

    override fun clear() {
        instance = null
    }
}

class SignUpViewModel(
    private val resourceRepository: ResourceRepository = ResourceRepository(),
    private val userRepository: UserRepository = UserRepository(),
) : ViewModel() {
    val email = TextData()
    val code = TextData()
    val message = MessageData()
    val firstName = TextData()
    val lastName = TextData()
    val password = TextData()
    val username = TextData()

    fun createAccountSubmit(onLoginSuccess: CoroutineScope.(userId: Int) -> Unit = {}) {
        registerUser(onLoginSuccess)
    }

    private fun registerUser(onLoginSuccess: CoroutineScope.(userId: Int) -> Unit = {}) {
        viewModelScope.launch {
            val registerRequest =
                UserRegisterRequest(
                    username.value.text,
                    firstName.value.text,
                    lastName.value.text,
                    email.value.text,
                    password.value.text
                )
            val userId = userRepository.userRegister(registerRequest)

            onLoginSuccess(userId)
        }
    }

    fun setDefaultUsername() {
        username.setText("${firstName.value.text}_${lastName.value.text}")
    }

    fun getConfirmationCode() {
        viewModelScope.launch(Dispatchers.IO) {
            resourceRepository.getConfirmationCode(email.value.text)
        }
    }

    fun checkConfirmationCode(onResponse: CoroutineScope.(isSuccessful: Boolean) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSuccessful =
                resourceRepository.checkConfirmationCode(email.value.text, code.value.text.toInt())
            onResponse(isSuccessful)
        }
    }
}
