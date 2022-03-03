package com.emamagic.application.interactor

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.repository.UserRepository
import javax.inject.Inject

class SinginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getServerConfig(hostName: String): ResultWrapper<*> = userRepository.getServerUpdate(hostName)

}