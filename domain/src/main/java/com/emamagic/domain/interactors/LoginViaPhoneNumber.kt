package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.repositories.UserRepository
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class LoginViaPhoneNumber @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): Interactor<LoginViaPhoneNumber.Params>(dispatchers) {


    override suspend fun buildUseCase(params: Params): ResultWrapper<*> =
        userRepository.loginViaPhoneNumber(params)


    data class Params(
        @SerializedName("phone_number")
        val phoneNumber: String
    )


}