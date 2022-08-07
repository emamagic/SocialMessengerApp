package com.emamagic.domain.entities

data class DirectReplyMessage(val id: String, var text: String, val userId: String, val type: String? = null)
