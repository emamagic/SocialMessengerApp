package com.emamagic.data.repositories

import com.emamagic.core.AuthUserScope
import com.emamagic.core.ResultWrapper
import com.emamagic.data.network.RestProvider
import com.emamagic.data.toResponse
import com.emamagic.data.toResult
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.interactors.conversation.GetMyConversations
import com.emamagic.domain.repositories.ConversationRepository
import com.emamagic.safe.SafeApi
import javax.inject.Inject

@AuthUserScope
class ConversationRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider
): SafeApi(), ConversationRepository {

    override suspend fun getCurrentConversation(): ResultWrapper<ConversationEntity> {
        TODO()
    }

    override suspend fun getMyConversations(params: GetMyConversations.Params): ResultWrapper<List<ConversationEntity>> = fresh {
        restProvider.conversationService.getConversations(params.workspaceId).toResponse()
    }.toResult()

}