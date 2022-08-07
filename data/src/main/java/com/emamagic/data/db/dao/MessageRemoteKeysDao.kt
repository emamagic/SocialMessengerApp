package com.emamagic.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.data.db.BaseDao
import com.emamagic.domain.entities.MessageRemoteKeys

@Dao
abstract class MessageRemoteKeysDao: BaseDao<MessageRemoteKeys> {

    @Query("SELECT * FROM message_remote_key WHERE id = :id")
    abstract suspend fun remoteKeysById(id: String): MessageRemoteKeys?

    @Query("DELETE FROM message_remote_key")
    abstract suspend fun clearRemoteKeys()

}