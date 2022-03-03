package com.emamagic.repository_impl

import com.emamagic.network.dto.ServerConfigDto
import com.emamagic.network.service.ConfigService
import com.emamagic.safe.SafeApi
import com.emamagic.safe.store.Resource
import javax.inject.Inject

class ServerConfigRepositoryImpl @Inject constructor(
    private val serverConfigService: ConfigService
): SafeApi() {



}