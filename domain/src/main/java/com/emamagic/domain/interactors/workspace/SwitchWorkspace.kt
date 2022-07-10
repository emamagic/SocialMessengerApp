package com.emamagic.domain.interactors.workspace

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.ResultInteractor
import com.emamagic.domain.repositories.WorkspaceRepository
import javax.inject.Inject

class SwitchWorkspace @Inject constructor(
    @Bridge
    private val workspaceRepository: WorkspaceRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<SwitchWorkspace.Params, WorkspaceEntity>(dispatchers) {


    override suspend fun buildUseCase(params: Params): ResultWrapper<WorkspaceEntity> =
        workspaceRepository.switchWorkspace(params)


    data class Params (val id: String)

}