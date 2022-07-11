package com.emamagic.domain.interactors.workspace

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.ResultInteractor
import com.emamagic.domain.repositories.WorkspaceRepository
import javax.inject.Inject

class CreateWorkspace @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    @Bridge
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<CreateWorkspace.Params, WorkspaceEntity>(dispatchers) {


    override suspend fun buildUseCase(params: Params): ResultWrapper<WorkspaceEntity> =
        workspaceRepository.createWorkspace(params)

    data class Params(
        val workspaceName: String,
        val avatarHash: String
    )

}