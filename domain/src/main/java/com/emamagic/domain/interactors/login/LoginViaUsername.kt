package com.emamagic.domain.interactors.login

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.interactors.Interactor
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class LoginViaUsername @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<LoginViaUsername.Params>(dispatchers) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<*> = userRepository.loginViaUserName(params)

    data class Params(
        val username: String,
        val pass: String
    )

}