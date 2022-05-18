package com.emamagic.base.interactor

import com.emamagic.repository.ConversationRepository
import javax.inject.Inject

class ConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {


}