package com.emamagic.data.repositories

import com.emamagic.cache.cache.get
import com.emamagic.cache.cache.pref
import com.emamagic.cache.cache.set
import com.emamagic.core.AuthUserScope
import com.emamagic.core.Error
import com.emamagic.core.PrefKeys
import com.emamagic.core.ResultWrapper
import com.emamagic.core.succeeded
import com.emamagic.data.db.dao.WorkspaceDao
import com.emamagic.data.network.RestProvider
import com.emamagic.data.toResponse
import com.emamagic.data.toResult
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.workspace.CreateWorkspace
import com.emamagic.domain.interactors.workspace.SwitchWorkspace
import com.emamagic.domain.repositories.WorkspaceRepository
import com.emamagic.safe.SafeApi
import javax.inject.Inject

@AuthUserScope
class WorkspaceRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    private val workspaceDao: WorkspaceDao
): SafeApi(), WorkspaceRepository {

    override suspend fun getMyWorkspaces(): ResultWrapper<List<WorkspaceEntity>> = fresh {
        restProvider.userService.getMyWorkspaces().toResponse()
    }.toResult(doOnSuccess = workspaceDao::insert, tryIfFailed = { workspaceDao.getWorkspaces() })

    override suspend fun getCurrentWorkspace(): ResultWrapper<WorkspaceEntity> {
        val currentWorkspaceId: String? = pref[PrefKeys.CURRENT_WORKSPACE_ID]
        return if (currentWorkspaceId.isNullOrEmpty()) {
            val myWorkspaces = getMyWorkspaces()
            if (myWorkspaces.succeeded) {
                val currentWorkspace = myWorkspaces.data!!.first()
                pref[PrefKeys.CURRENT_WORKSPACE_ID] = currentWorkspace.id
                ResultWrapper.Success(currentWorkspace)
            } else {
                ResultWrapper.Failed(myWorkspaces.error!!)
            }
        } else {
            val result = restProvider.workspaceService.findWorkspaceById(currentWorkspaceId).toResponse()
            if (result.isSuccessful()) {
                val currentWorkspace = result.body()
                pref[PrefKeys.CURRENT_WORKSPACE_ID] = currentWorkspace!!.id
                ResultWrapper.Success(currentWorkspace)
            } else {
                ResultWrapper.Failed(Error(message = result.errorBody()))
            }
        }

    }

    override suspend fun switchWorkspace(params: SwitchWorkspace.Params): ResultWrapper<WorkspaceEntity> = fresh {
        restProvider.workspaceService.findWorkspaceById(params.id).toResponse()
    }.toResult()

    override suspend fun createWorkspace(params: CreateWorkspace.Params): ResultWrapper<WorkspaceEntity> = fresh {
        restProvider.workspaceService.createWorkspace(params).toResponse()
    }.toResult()


}