package com.example.thread.data.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.example.thread.data.model.common.Message
import com.example.thread.data.model.common.MessageType

class TextData(defaultValue: String = "") {
    private var data by mutableStateOf(TextFieldValue(defaultValue))

    val value get() = data

    fun setText(newText: String) {
        data = TextFieldValue(newText)
    }

    fun setText(newText: TextFieldValue) {
        data = newText
    }
}

class MessageData {
    private var data by mutableStateOf<Message?>(null)

    val value get() = data

    fun setMessage(text: String, messageType: MessageType) {
        data = Message(text, messageType)
    }

    fun removeMessage() {
        data = null
    }
}
