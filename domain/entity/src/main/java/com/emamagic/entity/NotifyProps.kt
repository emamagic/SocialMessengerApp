package com.emamagic.entity

data class NotifyProps(
    val conversation: String,
    val desktop: String,
    val desktopSound: String,
    val email: String,
    val firstName: String,
    val mentionKeys: String,
    val push: String
)