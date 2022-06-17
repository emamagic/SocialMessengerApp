package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.PhoneNumber
import com.emamagic.domain.entities.ServerConfig
import com.emamagic.domain.entities.User
import com.emamagic.domain.entities.Workspace

interface UserRepository {

    suspend fun getServerUpdate(): ResultWrapper<ServerConfig>

    suspend fun phoneNumberRegistration(phoneNumber: PhoneNumber): ResultWrapper<Boolean>

    suspend fun otpVerification(code: String, phoneNumber: String, deviceId: String): ResultWrapper<User>

    suspend  fun getCurrentUser(shouldFetch: Boolean = false): ResultWrapper<User>

    suspend fun getMyWorkspaces(shouldFetch: Boolean = false): ResultWrapper<List<Workspace>>
}