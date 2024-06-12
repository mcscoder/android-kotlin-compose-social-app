package com.example.thread.data.model.common

data class Message(
    val text: String,
    val type: MessageType
)

enum class MessageType {
    OK,
    ERROR,
    WARNING,
}
