package com.emamagic.limoo.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.emamagic.data.db.AppDatabase
import com.emamagic.data.db.AppRoomDatabase
import com.emamagic.data.db.dao.WorkspaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppRoomDatabase {
        val builder = Room.databaseBuilder(context, AppRoomDatabase::class.java, "limooDB.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }


    @Provides
    fun provideWorkspaceDao(db: AppDatabase): WorkspaceDao = db.getWorkspaceDao()

}