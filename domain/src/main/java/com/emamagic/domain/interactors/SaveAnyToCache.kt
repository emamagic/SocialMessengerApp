package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class SaveAnyToCache @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
) : ResultInteractor<SaveAnyToCache.Params, Boolean>(dispatchers) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<Boolean> = userRepository.saveToCache(params)

    data class Params(
        val key: String,
        val value: Any?
    )

}