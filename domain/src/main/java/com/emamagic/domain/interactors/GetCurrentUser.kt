package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.User
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<Unit, User>(dispatchers) {

    override suspend fun buildUseCase(params: Unit): ResultWrapper<User> = userRepository.getCurrentUser()
}