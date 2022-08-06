package com.emamagic.conversations

import androidx.annotation.StringRes
import com.emamagic.domain.entities.ConversationEntity

sealed class ConversationWrapper(val idOrType: String) {
    class Header(type: String, @StringRes val displayNameId: Int, val items: List<ConversationEntity>, var isExpanded: Boolean): ConversationWrapper(type)
    class Conversation(id: String, val entity: ConversationEntity): ConversationWrapper(id)
}