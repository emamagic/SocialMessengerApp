package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.*
import com.emamagic.domain.interactors.*

interface UserRepository {

    suspend fun getServerConfig(params: GetServerConfig.Params): ResultWrapper<ServerConfig>

    suspend fun loginViaPhoneNumber(phoneNumber: LoginViaPhoneNumber.Params): ResultWrapper<Boolean>

    suspend fun verifyOtp(params: VerifyOtp.Params): ResultWrapper<User>

    suspend  fun getCurrentUser(): ResultWrapper<User>

    suspend fun getMyWorkspaces(): ResultWrapper<List<WorkspaceEntity>>

    suspend fun getMyOrganizations(): ResultWrapper<List<OrganizationEntity>>

    suspend fun loginViaUserName(username: LoginViaUsername.Params): ResultWrapper<Boolean>

    suspend fun getSessionByKeycloak(): ResultWrapper<Boolean>

    suspend fun saveAlias(data: SaveAlias.Params): ResultWrapper<Boolean>

    suspend fun isLoggedIn(): Boolean

    suspend fun hasIntroBeenSeen(): Boolean

    suspend fun userSawIntro()

    suspend fun signupUser(params: SignupUser.Params): ResultWrapper<User>


}