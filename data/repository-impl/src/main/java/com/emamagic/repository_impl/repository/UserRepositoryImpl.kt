package com.emamagic.repository_impl.repository

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.network.dto.ServerConfigDto
import com.emamagic.network.service.ConfigService
import com.emamagic.repository.UserRepository
import com.emamagic.safe.SafeApi
import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.store.Resource
import okhttp3.Cache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val serverConfigService: ConfigService
): SafeApi(), UserRepository {

    override suspend fun getServerUpdate(hostName: String): ResultWrapper<*> =
        get(
        key = "salam",
        memoryPolicy = MemoryPolicy(expires = 5000, shouldRefresh = { oldValue ->  (oldValue.data as ServerConfigDto).config.authServices == hostName })
        ) {
        serverConfigService.getServerConfig()
    }

}