package com.emamagic.application.interactor

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.entity.User
import com.emamagic.entity.Workspace
import com.emamagic.repository.UserRepository
import javax.inject.Inject

class CommonUserCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getCurrentUser(shouldFetch: Boolean = false): ResultWrapper<User> = userRepository.getCurrentUser(shouldFetch)

    suspend fun getMyWorkspaces(shouldFetch: Boolean = false): ResultWrapper<List<Workspace>> = userRepository.getMyWorkspaces(shouldFetch)

}