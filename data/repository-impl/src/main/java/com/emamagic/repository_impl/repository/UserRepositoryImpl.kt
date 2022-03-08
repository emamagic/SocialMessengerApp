package com.emamagic.repository_impl.repository

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.entity.PhoneNumber
import com.emamagic.entity.ServerConfig
import com.emamagic.entity.Status
import com.emamagic.network.service.ConfigService
import com.emamagic.repository.UserRepository
import com.emamagic.repository_impl.util.toError
import com.emamagic.repository_impl.util.toResponse
import com.emamagic.safe.SafeApi
import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.util.SafeWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val serverConfigService: ConfigService
) : SafeApi(), UserRepository {

    override suspend fun getServerUpdate(hostName: String): ResultWrapper<ServerConfig> =
        get(
            key = "salam",
            memoryPolicy = MemoryPolicy(
                expires = 5000,
                shouldRefresh = { it.data?.config?.authServices == hostName }
            ))
        {
            serverConfigService.getServerConfig().toResponse()
        }.run {
            when (this) {
                is SafeWrapper.Success -> ResultWrapper.Success(data!!)
                is SafeWrapper.Failed -> ResultWrapper.Failed(error.toError())
                is SafeWrapper.LoadingFetch -> TODO()
            }
        }

    override suspend fun submitPhoneNumber(phoneNumber: PhoneNumber): ResultWrapper<Status> {
        TODO("Not yet implemented")
    }

}