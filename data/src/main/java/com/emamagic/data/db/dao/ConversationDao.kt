package com.emamagic.data.db.dao

import androidx.room.Dao
import com.emamagic.data.db.BaseDao
import com.emamagic.domain.entities.ConversationEntity

@Dao
abstract class ConversationDao: BaseDao<ConversationEntity> {
}