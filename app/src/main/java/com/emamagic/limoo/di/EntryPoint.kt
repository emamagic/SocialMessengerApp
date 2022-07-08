package com.emamagic.limoo.di

import com.emamagic.data.network.RestProvider
import com.emamagic.domain.repositories.FileRepository
import com.emamagic.domain.repositories.UserRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@InstallIn(AuthUserComponent::class)
@EntryPoint
interface AuthEntryPoint {
    fun getUserRepository(): UserRepository
    fun getFileRepository(): FileRepository
    fun getRestProvider(): RestProvider
}