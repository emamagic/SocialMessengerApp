package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.PhoneNumber
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class PhoneNumberRegistration @Inject constructor(
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<PhoneNumberRegistration.Params>(dispatchers) {


    override suspend fun buildUseCase(params: Params): ResultWrapper<*> =
        userRepository.phoneNumberRegistration(params.phoneNumber)


    data class Params(
        val phoneNumber: PhoneNumber
    )


}