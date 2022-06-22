package com.emamagic.domain.interactors

import com.emamagic.core.*
import com.emamagic.domain.entities.User
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class VerifyOtp @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatcher: AppCoroutineDispatchers
): ResultInteractor<VerifyOtp.Params, User>(dispatcher) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<User> =
        userRepository.verifyOtp(params)

    data class Params(
        val code: String,
        val phoneNumber: String,
        val deviceId: String
    )

}