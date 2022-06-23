package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class LoginWithKeycloak @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<Unit>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<*> = userRepository.getSessionByKeycloak()
}