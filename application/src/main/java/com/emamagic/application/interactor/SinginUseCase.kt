package com.emamagic.application.interactor

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.entity.PhoneNumber
import com.emamagic.entity.ServerConfig
import com.emamagic.entity.Status
import com.emamagic.repository.UserRepository
import javax.inject.Inject

class SinginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getServerConfig(hostName: String): ResultWrapper<ServerConfig> = userRepository.getServerUpdate(hostName)

    suspend fun submitPhoneNumber(phoneNumber: PhoneNumber): ResultWrapper<Status> = userRepository.submitPhoneNumber(phoneNumber)

}