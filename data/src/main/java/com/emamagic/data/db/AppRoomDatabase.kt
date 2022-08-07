package com.emamagic.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.entities.OrganizationEntity
import com.emamagic.domain.entities.WorkspaceEntity

@Database(
    entities = [
        ConversationEntity::class,
        WorkspaceEntity::class,
        OrganizationEntity::class
    ], version = 18, exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppRoomDatabase : RoomDatabase(), AppDatabase