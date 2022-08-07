package com.emamagic.domain.interactors.conversation

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.interactors.ResultInteractor
import com.emamagic.domain.repositories.ConversationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyConversations @Inject constructor(
    @Bridge
    private val conversationRepository: ConversationRepository
) {

    operator fun invoke(params: Params): Flow<ResultWrapper<List<ConversationEntity>>> =
        conversationRepository.getMyConversations(params)

    data class Params(
        val workspaceId: String
    )

}