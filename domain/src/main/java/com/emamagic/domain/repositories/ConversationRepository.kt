package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.interactors.conversation.GetMyConversations
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {

    suspend fun getCurrentConversation(): ResultWrapper<ConversationEntity>

    fun getMyConversations(params: GetMyConversations.Params): Flow<ResultWrapper<List<ConversationEntity>>>

}