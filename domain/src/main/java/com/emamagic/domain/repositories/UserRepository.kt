package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.*
import com.emamagic.domain.interactors.LoginWithPhoneNumber
import com.emamagic.domain.interactors.LoginWithUsername
import com.emamagic.domain.interactors.SaveToCache
import com.emamagic.domain.interactors.VerifyOtp

interface UserRepository {

    suspend fun updateServerUpdate(serverHost: String): ResultWrapper<ServerConfig>

    suspend fun loginWithPhoneNumber(phoneNumber: LoginWithPhoneNumber.Params): ResultWrapper<Boolean>

    suspend fun verifyOtp(params: VerifyOtp.Params): ResultWrapper<User>

    suspend  fun getCurrentUser(shouldFetch: Boolean = false): ResultWrapper<User>

    suspend fun getMyWorkspaces(shouldFetch: Boolean = false): ResultWrapper<List<Workspace>>

    suspend fun loginWithUserName(username: LoginWithUsername.Params): ResultWrapper<Boolean>

    suspend fun getSessionByKeycloak(): ResultWrapper<Boolean>

    suspend fun saveToCache(data: SaveToCache.Params): ResultWrapper<Boolean>
}