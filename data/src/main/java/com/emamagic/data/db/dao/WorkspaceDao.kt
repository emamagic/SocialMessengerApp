package com.emamagic.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.data.db.BaseDao
import com.emamagic.domain.entities.Workspace
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WorkspaceDao: BaseDao<Workspace> {

    @Query("SELECT * FROM workspace")
     abstract fun getAWorkspaces(): Flow<List<Workspace>>

}