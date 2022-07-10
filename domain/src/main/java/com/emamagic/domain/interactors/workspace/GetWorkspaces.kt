package com.emamagic.domain.interactors.workspace

import com.emamagic.core.*
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.ResultInteractor
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class GetWorkspaces @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<Unit, List<WorkspaceEntity>>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<List<WorkspaceEntity>> = userRepository.getMyWorkspaces()

}