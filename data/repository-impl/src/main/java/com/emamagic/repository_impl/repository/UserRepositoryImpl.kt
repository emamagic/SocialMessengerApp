package com.emamagic.repository_impl.repository

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.network.service.ConfigService
import com.emamagic.repository.UserRepository
import com.emamagic.safe.SafeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val serverConfigService: ConfigService
): SafeApi(), UserRepository {

    override suspend fun getServerUpdate(hostName: String): ResultWrapper<*> = get {
        serverConfigService.getServerConfig()
    }



}