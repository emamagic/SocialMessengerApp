package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class SaveAlias @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
) : ResultInteractor<SaveAlias.Params, Boolean>(dispatchers) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<Boolean> = userRepository.saveAlias(params)

    data class Params(
        val alias: String
    )

}