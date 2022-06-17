package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.ServerConfig
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateServerConfig @Inject constructor(
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<Unit>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<*> =
        userRepository.getServerUpdate()


}