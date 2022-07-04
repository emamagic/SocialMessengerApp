package com.emamagic.limoo.di

import com.emamagic.data.db.AppDatabase
import com.emamagic.data.db.AppRoomDatabase
import com.emamagic.data.db.DatabaseTransactionRunner
import com.emamagic.data.db.RoomTransactionRunner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DbBinderModule {

    @Singleton
    @Binds
    abstract fun provideDatabaseTransactionRunner(runner: RoomTransactionRunner): DatabaseTransactionRunner

    @Binds
    abstract fun bindAppDatabase(db: AppRoomDatabase): AppDatabase


}