package com.emamagic.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emamagic.domain.entities.Conversation
import com.emamagic.domain.entities.Workspace

@Database(
    entities = [
        Conversation::class,
        Workspace::class
    ], version = 2, exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppRoomDatabase : RoomDatabase(), AppDatabase