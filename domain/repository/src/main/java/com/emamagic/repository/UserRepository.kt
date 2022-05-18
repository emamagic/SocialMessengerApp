package com.emamagic.repository

import com.emamagic.core.ResultWrapper
import com.emamagic.entity.*

interface UserRepository {

    suspend fun getServerUpdate(hostName: String): ResultWrapper<ServerConfig>

    suspend fun phoneNumberRegistration(phoneNumber: PhoneNumber): ResultWrapper<Boolean>

    suspend fun otpVerification(code: String, phoneNumber: String, deviceId: String): ResultWrapper<User>

    suspend  fun getCurrentUser(shouldFetch: Boolean = false): ResultWrapper<User>

    suspend fun getMyWorkspaces(shouldFetch: Boolean = false): ResultWrapper<List<Workspace>>
}