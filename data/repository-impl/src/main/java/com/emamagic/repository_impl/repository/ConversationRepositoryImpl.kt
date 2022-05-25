package com.emamagic.repository_impl.repository

import com.emamagic.network.service.ConversationService
import com.emamagic.repository.ConversationRepository
import com.emamagic.safe.SafeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepositoryImpl @Inject constructor(
): SafeApi(), ConversationRepository {
}