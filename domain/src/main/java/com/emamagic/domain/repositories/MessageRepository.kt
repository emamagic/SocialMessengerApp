package com.emamagic.domain.repositories

import androidx.paging.PagingData
import com.emamagic.domain.entities.MessageEntity
import com.emamagic.domain.interactors.GetMessages
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getMessages(params: GetMessages.Params): Flow<PagingData<MessageEntity>>
}