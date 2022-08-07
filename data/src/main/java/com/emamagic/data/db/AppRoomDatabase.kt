package com.emamagic.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emamagic.domain.entities.*

@Database(
    entities = [
        ConversationEntity::class,
        WorkspaceEntity::class,
        OrganizationEntity::class,
        MessageEntity::class,
        MessageRemoteKeys::class
    ], version = 20, exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppRoomDatabase : RoomDatabase(), AppDatabase