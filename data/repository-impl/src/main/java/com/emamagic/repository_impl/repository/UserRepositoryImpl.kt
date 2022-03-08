package com.emamagic.repository_impl.repository

import com.emamagic.common_jvm.ServerConfigResult
import com.emamagic.entity.ServerConfig
import com.emamagic.network.service.ConfigService
import com.emamagic.repository.UserRepository
import com.emamagic.repository_impl.mapper.ResponseMapper
import com.emamagic.safe.SafeApi
import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.util.ResultWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val serverConfigService: ConfigService
) : SafeApi(), UserRepository {

    override suspend fun getServerUpdate(hostName: String): ServerConfigResult =
        get(
            key = "salam",
            memoryPolicy = MemoryPolicy(
                expires = 5000,
                shouldRefresh = { it.data?.config?.authServices == hostName }
            ))
        {
            ResponseMapper(serverConfigService.getServerConfig())
        }.run {
            when (this) {
                is ResultWrapper.Success -> ServerConfigResult.Success(data!!)
                is ResultWrapper.Failed -> ServerConfigResult.Error(error?.message ?: "error")
                is ResultWrapper.Loading -> ServerConfigResult.Loading
            }
        }

}