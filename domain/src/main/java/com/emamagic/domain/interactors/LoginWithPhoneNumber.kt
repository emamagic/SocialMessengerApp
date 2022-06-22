package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.repositories.UserRepository
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class LoginWithPhoneNumber @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<LoginWithPhoneNumber.Params>(dispatchers) {


    override suspend fun buildUseCase(params: Params): ResultWrapper<*> =
        userRepository.loginWithPhoneNumber(params)


    data class Params(
        @SerializedName("phone_number")
        val phoneNumber: String
    )


}