package com.emamagic.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.data.db.BaseDao
import com.emamagic.domain.entities.ConversationEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ConversationDao: BaseDao<ConversationEntity> {

    @Query("SELECT * FROM conversation WHERE workspaceId = :workspaceId")
    abstract fun loadConversations(workspaceId: String): Flow<List<ConversationEntity>>

    @Query("DELETE FROM conversation")
    abstract suspend fun deleteAll()

    @Query("DELETE FROM conversation WHERE id = :id")
    abstract suspend fun deleteById(id: String)
}