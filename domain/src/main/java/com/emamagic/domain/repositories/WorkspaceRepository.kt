package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.workspace.CreateWorkspace
import com.emamagic.domain.interactors.workspace.SwitchWorkspace

interface WorkspaceRepository {

    suspend fun getCurrentWorkspace(): ResultWrapper<WorkspaceEntity>

    suspend fun switchWorkspace(params: SwitchWorkspace.Params): ResultWrapper<WorkspaceEntity>

    suspend fun createWorkspace(params: CreateWorkspace.Params): ResultWrapper<WorkspaceEntity>

    suspend fun getMyWorkspaces(): ResultWrapper<List<WorkspaceEntity>>

}