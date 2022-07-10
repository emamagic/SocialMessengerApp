package com.emamagic.domain.interactors.login

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.domain.repositories.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsLoggedIn @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    private val dispatchers: AppCoroutineDispatchers
) {

    suspend operator fun invoke(): Boolean = withContext(dispatchers.io) { userRepository.isLoggedIn() }

}