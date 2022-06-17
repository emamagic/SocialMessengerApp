package com.emamagic.domain.interactors

import com.emamagic.core.*
import com.emamagic.domain.entities.User
import com.emamagic.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OtpVerification @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatcher: AppCoroutineDispatchers
): ResultInteractor<OtpVerification.Params, User>(dispatcher) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<User> =
        userRepository.otpVerification(params.code, params.phoneNumber, params.deviceId)


    data class Params(
        val code: String,
        val phoneNumber: String,
        val deviceId: String
    )

}