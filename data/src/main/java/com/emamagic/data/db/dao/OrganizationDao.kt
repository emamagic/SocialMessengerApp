package com.emamagic.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emamagic.data.db.BaseDao
import com.emamagic.domain.entities.OrganizationEntity
import com.emamagic.domain.entities.OrganizationWithWorkspaces

@Dao
abstract class OrganizationDao: BaseDao<OrganizationEntity> {

    @Transaction
    @Query("SELECT * FROM organization")
    abstract suspend fun getOrganizationWithWorkspaces(): OrganizationWithWorkspaces

    @Query("SELECT * FROM organization")
    abstract suspend fun getOrganizations(): List<OrganizationEntity>
}