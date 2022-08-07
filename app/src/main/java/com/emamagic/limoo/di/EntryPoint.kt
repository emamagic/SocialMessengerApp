package com.emamagic.limoo.di

import com.emamagic.data.network.RestProvider
import com.emamagic.domain.repositories.*
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@InstallIn(AuthUserComponent::class)
@EntryPoint
interface AuthEntryPoint {
    fun getUserRepository(): UserRepository
    fun getFileRepository(): FileRepository
    fun getWorkspaceRepository(): WorkspaceRepository
    fun getConversationRepository(): ConversationRepository
    fun getMessageRepository(): MessageRepository
    fun getRestProvider(): RestProvider
}