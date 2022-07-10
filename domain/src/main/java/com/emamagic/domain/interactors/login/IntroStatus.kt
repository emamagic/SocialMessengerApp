package com.emamagic.domain.interactors.login

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.domain.repositories.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IntroStatus @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    private val dispatchers: AppCoroutineDispatchers
)  {

    suspend fun userSawIntro() = withContext(dispatchers.io) { userRepository.userSawIntro() }

    suspend fun hasIntroBeenSeen() = withContext(dispatchers.io) { userRepository.hasIntroBeenSeen() }

}