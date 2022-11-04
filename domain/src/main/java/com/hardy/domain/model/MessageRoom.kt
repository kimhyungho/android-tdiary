package com.hardy.domain.model

data class MessageRoom(
    val users: Map<String, Boolean>? = HashMap(),
    val messages: Map<String, Message>? = HashMap(),
)