package com.emamagic.domain.interactors

import com.emamagic.core.*
import com.emamagic.domain.entities.OrganizationWithWorkspaces
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorkspaces @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<Unit, List<WorkspaceEntity>>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<List<WorkspaceEntity>> = userRepository.getMyWorkspaces()

}