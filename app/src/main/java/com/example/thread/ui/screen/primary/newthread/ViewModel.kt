package com.example.thread.ui.screen.primary.newthread

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.data.model.thread.ThreadType
import com.example.thread.data.repository.thread.ThreadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewThreadViewModel : ViewModel() {
    private val threadRepository: ThreadRepository = ThreadRepository()

    var textContent by mutableStateOf(TextFieldValue())
        private set
    var imageFiles by mutableStateOf<List<ByteArray>>(emptyList())
        private set

    var currImageUrls by mutableStateOf<List<String>>(emptyList())
        private set

    var deletedImageUrls by mutableStateOf<List<String>>(emptyList())
        private set

    fun updateText(newTextContent: TextFieldValue) {
        textContent = newTextContent
    }

    fun updateText(newTextContent: String) {
        textContent = TextFieldValue(newTextContent)
    }

    fun updateImageFiles(newImageFiles: List<ByteArray>) {
        imageFiles = newImageFiles
    }

    fun removeImageFile(index: Int) {
        val newImageFiles = imageFiles.toMutableList()
        newImageFiles.removeAt(index)
        imageFiles = newImageFiles
    }

    fun setImageUrls(newImageUrls: List<String>) {
        currImageUrls = newImageUrls
    }

    fun removeImageUrl(index: Int) {
        // Add removed image url to deleted list
        val newDeletedImageUrls = deletedImageUrls.toMutableList()
        newDeletedImageUrls.add(currImageUrls[index])
        deletedImageUrls = newDeletedImageUrls

        // Remove out of current image url list
        val newImageUrls = currImageUrls.toMutableList()
        newImageUrls.removeAt(index)
        currImageUrls = newImageUrls
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
                onResponse()
            }
        }
    }

    fun postReply(
        mainThreadType: Int,
        mainId: Int,
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

    fun updateThread(threadId: Int, onResponse: CoroutineScope.() -> Unit = {}) {
        CoroutineScope(Dispatchers.IO).launch {
            threadRepository.updateThread(threadId, textContent.text, deletedImageUrls, imageFiles)
            onResponse()
        }
    }
}
