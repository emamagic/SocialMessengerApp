package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.interactors.conversation.GetMyConversations

interface ConversationRepository {

    suspend fun getCurrentConversation(): ResultWrapper<ConversationEntity>

    suspend fun getMyConversations(params: GetMyConversations.Params): ResultWrapper<List<ConversationEntity>>

}