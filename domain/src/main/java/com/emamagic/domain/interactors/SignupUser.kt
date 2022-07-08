package com.emamagic.domain.interactors

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.User
import com.emamagic.domain.repositories.UserRepository
import javax.inject.Inject

class SignupUser @Inject constructor(
    @Bridge
    private val userRepository: UserRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<SignupUser.Params, User>(dispatchers) {


    override suspend fun buildUseCase(params: Params): ResultWrapper<User> =
        userRepository.signupUser(params)

    data class Params(
        var firstName: String? = null,
        var lastName: String? = null,
        var email: String? = null,
        var phoneNumber: String? = null,
        var avatarHash: String? = null
    )

}
