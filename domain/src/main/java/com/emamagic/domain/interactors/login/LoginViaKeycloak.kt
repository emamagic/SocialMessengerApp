package com.emamagic.domain.interactors.login

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.interactors.Interactor
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class LoginViaKeycloak @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<Unit>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<*> = userRepository.getSessionByKeycloak()
}