package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
) {
}