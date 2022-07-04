package com.emamagic.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.data.db.BaseDao
import com.emamagic.domain.entities.WorkspaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WorkspaceDao: BaseDao<WorkspaceEntity> {

    @Query("SELECT * FROM workspace")
     abstract fun observeWorkspaces(): Flow<List<WorkspaceEntity>>

    @Query("SELECT * FROM workspace")
    abstract fun getWorkspaces(): List<WorkspaceEntity>


     @Query("SELECT * FROM workspace WHERE id = :id")
     abstract suspend fun getWorkspaceById(id: String): WorkspaceEntity

    @Query("DELETE from workspace")
    abstract suspend fun deleteAll()

}