package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.*
import com.emamagic.domain.interactors.*

interface UserRepository {

    suspend fun updateServerConfig(params: UpdateServerConfig.Params): ResultWrapper<ServerConfig>

    suspend fun loginWithPhoneNumber(phoneNumber: LoginWithPhoneNumber.Params): ResultWrapper<Boolean>

    suspend fun verifyOtp(params: VerifyOtp.Params): ResultWrapper<User>

    suspend  fun getCurrentUser(): ResultWrapper<User>

    suspend fun getMyWorkspaces(): ResultWrapper<List<Workspace>>

    suspend fun loginWithUserName(username: LoginWithUsername.Params): ResultWrapper<Boolean>

    suspend fun getSessionByKeycloak(): ResultWrapper<Boolean>

    suspend fun saveAlias(data: SaveAlias.Params): ResultWrapper<Boolean>

}