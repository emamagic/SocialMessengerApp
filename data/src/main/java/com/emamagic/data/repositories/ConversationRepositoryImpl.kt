package com.emamagic.data.repositories

import com.emamagic.core.AuthUserScope
import com.emamagic.domain.repositories.ConversationRepository
import javax.inject.Inject

@AuthUserScope
class ConversationRepositoryImpl @Inject constructor(
): ConversationRepository {
}