package com.emamagic.limoo.di

import com.emamagic.data_android.interceptor.network.RestProvider
import com.emamagic.domain.repositories.UserRepository
import com.emamagic.limoo.di.AuthUserComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@InstallIn(AuthUserComponent::class)
@EntryPoint
interface AuthEntryPoint {
    fun getUserRepository(): UserRepository
    fun getRestProvider(): RestProvider
}