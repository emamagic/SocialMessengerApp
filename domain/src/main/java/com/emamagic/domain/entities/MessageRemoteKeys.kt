package com.emamagic.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_remote_key")
data class MessageRemoteKeys(
    @PrimaryKey
    override val id: String,
    val prevKey: Int?,
    val nextKey: Int?
): BaseEntity