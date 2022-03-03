package com.emamagic.network.service

import com.emamagic.network.dto.ServerConfigDto
import retrofit2.http.GET

interface ConfigService {

    @GET("config")
    suspend fun getServerConfig(): ServerConfigDto

}