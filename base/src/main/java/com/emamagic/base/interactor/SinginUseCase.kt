package com.emamagic.base.interactor

import com.emamagic.core.ResultWrapper
import com.emamagic.entity.PhoneNumber
import com.emamagic.entity.ServerConfig
import com.emamagic.entity.User
import com.emamagic.repository.UserRepository
import javax.inject.Inject

class SinginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getServerConfig(hostName: String): ResultWrapper<ServerConfig> = userRepository.getServerUpdate(hostName)

    suspend fun phoneNumberRegistration(phoneNumber: PhoneNumber): ResultWrapper<Boolean> = userRepository.phoneNumberRegistration(phoneNumber)

    suspend fun otpVerification(code: String, phoneNumber: String, deviceId: String): ResultWrapper<User> = userRepository.otpVerification(code, phoneNumber, deviceId)


}