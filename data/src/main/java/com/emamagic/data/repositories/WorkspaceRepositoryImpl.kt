package com.emamagic.data.repositories

import com.emamagic.core.AuthUserScope
import com.emamagic.core.ResultWrapper
import com.emamagic.data.network.RestProvider
import com.emamagic.data.toResponse
import com.emamagic.data.toResult
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.workspace.SwitchWorkspace
import com.emamagic.domain.repositories.WorkspaceRepository
import com.emamagic.safe.SafeApi
import javax.inject.Inject

@AuthUserScope
class WorkspaceRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider
): SafeApi(), WorkspaceRepository {


    override suspend fun getCurrentWorkspace(): ResultWrapper<WorkspaceEntity> {
        return ResultWrapper.Success(WorkspaceEntity(id = ""))
    }

    override suspend fun switchWorkspace(params: SwitchWorkspace.Params): ResultWrapper<WorkspaceEntity> = fresh {
        restProvider.workspaceService.getWorkspaceById(params.id).toResponse()
    }.toResult()


}