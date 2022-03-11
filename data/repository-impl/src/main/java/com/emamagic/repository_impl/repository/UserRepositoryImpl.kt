package com.emamagic.repository_impl.repository

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.entity.PhoneNumber
import com.emamagic.entity.ServerConfig
import com.emamagic.entity.User
import com.emamagic.entity.Workspace
import com.emamagic.network.service.ConfigService
import com.emamagic.network.service.UserService
import com.emamagic.repository.UserRepository
import com.emamagic.repository_impl.util.toResponse
import com.emamagic.repository_impl.util.toResult
import com.emamagic.safe.SafeApi
import com.emamagic.safe.policy.MemoryPolicy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val serverConfigService: ConfigService,
    private val userService: UserService,
) : SafeApi(), UserRepository {

    override suspend fun getServerUpdate(hostName: String): ResultWrapper<ServerConfig> = fresh {
            serverConfigService.getServerConfig().toResponse()
        }.toResult()

    override suspend fun phoneNumberRegistration(phoneNumber: PhoneNumber): ResultWrapper<Boolean> = fresh {
        userService.phoneRegistration(phoneNumber).toResponse()
    }.toResult(shouldReturn = true)

    override suspend fun otpVerification(code: String, phoneNumber: String, deviceId: String): ResultWrapper<User> = fresh {
        userService.otpVerification(phoneNumber, code, deviceId).toResponse()
    }.toResult()

    override suspend fun getCurrentUser(shouldFetch: Boolean): ResultWrapper<User> = get("CurrentUser" ,
        memoryPolicy = MemoryPolicy(shouldRefresh = { shouldFetch })) {
        userService.getCurrentUser().toResponse()
    }.toResult()

    override suspend fun getMyWorkspaces(shouldFetch: Boolean): ResultWrapper<List<Workspace>> = get("MyWorkspaces",
        memoryPolicy = MemoryPolicy(shouldRefresh = { shouldFetch })) {
        userService.getMyWorkspaces().toResponse()
    }.toResult()

}