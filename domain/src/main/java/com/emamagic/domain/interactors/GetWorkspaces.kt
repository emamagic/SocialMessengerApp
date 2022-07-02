package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.Workspace
import com.emamagic.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWorkspaces @Inject constructor(
    @Bridge
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<ResultWrapper<List<Workspace>>> =
        userRepository.getMyWorkspaces()

}