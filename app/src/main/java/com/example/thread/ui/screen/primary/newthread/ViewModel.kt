package com.example.thread.ui.screen.primary.newthread

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NewThreadViewModelProvider {
    fun getInstance(threadNavController: ThreadNavController): NewThreadViewModel {
        return NewThreadViewModel(threadNavController)
    }
}

class NewThreadViewModel(
    private val threadNavController: ThreadNavController,
    private val threadRepository: ThreadRepository = ThreadRepository(),
) : ViewModel() {
    private val currentUser = GlobalViewModelProvider.getCurrentUserId()

    var textContent by mutableStateOf(TextFieldValue())
        private set
    var imageFiles by mutableStateOf<List<ByteArray>>(emptyList())
        private set

    fun updateText(newTextContent: TextFieldValue) {
        textContent = newTextContent
    }

    fun updateImageFiles(newImageFiles: List<ByteArray>) {
        imageFiles = newImageFiles
    }

    fun removeImageFile(index: Int) {
        val newImageFiles = imageFiles.toMutableList()
        newImageFiles.removeAt(index)
        imageFiles = newImageFiles
    }

    fun postThread() {
        viewModelScope.launch(context = Dispatchers.IO) {
            if (textContent.text.isNotEmpty()) {
                threadRepository.postThread(
                    text = textContent.text,
                    imageFiles = imageFiles,
                    userId = currentUser,
                )
                launch(context = Dispatchers.Main) {
                    threadNavController.navigateUp()
                }
            }
        }
    }

    fun postThreadReply(mainThreadId: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            if (textContent.text.isNotEmpty()) {
                threadRepository.postThreadReply(
                    text = textContent.text,
                    imageFiles = imageFiles,
                    userId = currentUser,
                    mainThreadId = mainThreadId,
                )
                launch(context = Dispatchers.Main) {
                    threadNavController.navigateUp()
                }
            }
        }
    }

    fun postThreadReplyingReply(threadReplyId: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            if (textContent.text.isNotEmpty()) {
                threadRepository.postThreadReplyingReply(
                    text = textContent.text,
                    imageFiles = imageFiles,
                    userId = currentUser,
                    threadReplyId = threadReplyId
                )
                launch(context = Dispatchers.Main) {
                    threadNavController.navigateUp()
                }
            }
        }
    }
}
