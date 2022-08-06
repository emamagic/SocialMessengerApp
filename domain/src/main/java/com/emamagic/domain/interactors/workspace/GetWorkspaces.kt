package com.emamagic.domain.interactors.workspace

import com.emamagic.core.*
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.ResultInteractor
import com.emamagic.domain.repositories.WorkspaceRepository
import javax.inject.Inject

class GetWorkspaces @Inject constructor(
    @Bridge
    private val workspaceRepository: WorkspaceRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<Unit, List<WorkspaceEntity>>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<List<WorkspaceEntity>> = workspaceRepository.getMyWorkspaces()

}