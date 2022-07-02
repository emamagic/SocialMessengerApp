package com.emamagic.data.db

import com.emamagic.data.db.dao.WorkspaceDao

interface AppDatabase {
    fun getWorkspaceDao(): WorkspaceDao
}