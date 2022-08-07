package com.emamagic.data.db

import com.emamagic.data.db.dao.*

interface AppDatabase {
    fun getWorkspaceDao(): WorkspaceDao
    fun getOrganizationDao(): OrganizationDao
    fun getConversationDao(): ConversationDao
    fun getMessageDao(): MessageDao
    fun getMessageRemoteKeysDao(): MessageRemoteKeysDao
}