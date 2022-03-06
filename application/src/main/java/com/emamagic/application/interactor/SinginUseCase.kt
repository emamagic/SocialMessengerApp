package com.emamagic.application.interactor

import com.emamagic.common_jvm.ServerConfigResult
import com.emamagic.repository.UserRepository
import javax.inject.Inject

class SinginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getServerConfig(hostName: String): ServerConfigResult = userRepository.getServerUpdate(hostName)

}