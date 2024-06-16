package com.example.thread.ui.screen.primary.newthread

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.data.model.thread.ThreadType
import com.example.thread.data.repository.thread.ThreadRepository
import com.example.thread.ui.navigation.ThreadNavController
import kotlinx.coroutines.CoroutineScope
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

    fun postThread(
        threadType: ThreadType = ThreadType.POST,
        mainId: Int? = null,
        onResponse: CoroutineScope.() -> Unit = {},
    ) {
        viewModelScope.launch(context = Dispatchers.IO) {
            if (textContent.text.isNotEmpty()) {
                threadRepository.postThread(
                    text = textContent.text,
                    type = threadType.ordinal,
                    imageFiles = imageFiles,
                    mainId = mainId
                )
                launch(context = Dispatchers.Main) {
                    threadNavController.navigateUp()
                }
                onResponse()
            }
        }
    }

    fun postReply(
        mainThreadType: Int,
        mainId: Int? = null,
        onResponse: CoroutineScope.() -> Unit = {},
    ) {
        var type: ThreadType = ThreadType.COMMENT
        when (mainThreadType) {
            ThreadType.COMMENT.ordinal -> type = ThreadType.REPLY
        }
        postThread(type, mainId) {
            onResponse()
        }
    }
}
