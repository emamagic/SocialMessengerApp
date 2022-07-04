package com.emamagic.domain.interactors

import com.emamagic.core.*
import com.emamagic.domain.entities.OrganizationEntity
import com.emamagic.domain.entities.OrganizationWithWorkspaces
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrganizations @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<Unit, List<OrganizationEntity>>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<List<OrganizationEntity>> = userRepository.getMyOrganizations()


}