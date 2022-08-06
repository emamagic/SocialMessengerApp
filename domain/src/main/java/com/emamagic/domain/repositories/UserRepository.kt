package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.*
import com.emamagic.domain.interactors.*
import com.emamagic.domain.interactors.login.LoginViaPhoneNumber
import com.emamagic.domain.interactors.login.LoginViaUsername
import com.emamagic.domain.interactors.login.SignupUser
import com.emamagic.domain.interactors.login.VerifyOtp

interface UserRepository {

    suspend fun getServerConfig(params: GetServerConfig.Params): ResultWrapper<ServerConfig>

    suspend fun loginViaPhoneNumber(phoneNumber: LoginViaPhoneNumber.Params): ResultWrapper<Boolean>

    suspend fun verifyOtp(params: VerifyOtp.Params): ResultWrapper<User>

    suspend  fun getCurrentUser(): ResultWrapper<User>

    suspend fun getMyOrganizations(): ResultWrapper<List<OrganizationEntity>>

    suspend fun loginViaUserName(username: LoginViaUsername.Params): ResultWrapper<Boolean>

    suspend fun getSessionByKeycloak(): ResultWrapper<Boolean>

    suspend fun saveAlias(data: SaveAlias.Params): ResultWrapper<Boolean>

    suspend fun isLoggedIn(): Boolean

    suspend fun hasIntroBeenSeen(): Boolean

    suspend fun userSawIntro()

    suspend fun signupUser(params: SignupUser.Params): ResultWrapper<User>


}