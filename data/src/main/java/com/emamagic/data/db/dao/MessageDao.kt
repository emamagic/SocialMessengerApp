package com.emamagic.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.emamagic.data.db.BaseDao
import com.emamagic.domain.entities.MessageEntity

@Dao
abstract class MessageDao: BaseDao<MessageEntity> {

    @Query("SELECT * FROM message WHERE conversationId = :conversationId")
    abstract fun loadMessages(conversationId: String): PagingSource<Int, MessageEntity>

    @Query("DELETE FROM message")
    abstract suspend fun deleteAll()

}