package com.emamagic.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Conversation(
    @PrimaryKey
    val id: String
)