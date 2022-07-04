package com.emamagic.data.db

import com.emamagic.data.db.dao.OrganizationDao
import com.emamagic.data.db.dao.WorkspaceDao

interface AppDatabase {
    fun getWorkspaceDao(): WorkspaceDao
    fun getOrganizationDao(): OrganizationDao
}