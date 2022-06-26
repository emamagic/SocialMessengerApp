package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.ServerConfig
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateServerConfig @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
) : ResultInteractor<UpdateServerConfig.Params, ServerConfig>(dispatchers) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<ServerConfig> =
        userRepository.updateServerConfig(params)


    data class Params(
        val serverHost: String,
        val shouldRefresh: Boolean
    )

}