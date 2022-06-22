package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class LoginWithUsername @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<LoginWithUsername.Params>(dispatchers) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<*> = userRepository.loginWithUserName(params)

    data class Params(
        val username: String,
        val pass: String
    )

}