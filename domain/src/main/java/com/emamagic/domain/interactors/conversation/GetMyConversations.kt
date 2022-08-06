package com.emamagic.domain.interactors.conversation

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.interactors.ResultInteractor
import com.emamagic.domain.repositories.ConversationRepository
import javax.inject.Inject

class GetMyConversations @Inject constructor(
    @Bridge
    private val conversationRepository: ConversationRepository,
    dispatchers: AppCoroutineDispatchers
): ResultInteractor<GetMyConversations.Params, List<ConversationEntity>>(dispatchers) {

    override suspend fun buildUseCase(params: Params): ResultWrapper<List<ConversationEntity>> =
        conversationRepository.getMyConversations(params)

    data class Params(
        val workspaceId: String
    )

}