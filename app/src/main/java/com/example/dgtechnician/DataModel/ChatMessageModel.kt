package com.example.dgtechnician.DataModel

data class ChatMessageModel(
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val timestamp: Long = 0
)