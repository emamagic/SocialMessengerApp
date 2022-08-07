package com.emamagic.domain.interactors

import androidx.paging.PagingData
import com.emamagic.core.Bridge
import com.emamagic.domain.entities.MessageEntity
import com.emamagic.domain.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessages @Inject constructor(
    @Bridge
    private val repository: MessageRepository
){

    operator fun invoke(params: Params): Flow<PagingData<MessageEntity>> =
        repository.getMessages(params)

    data class Params(
        val workspaceId: String,
        val conversationId: String,
        val lastViewAt: Long
    )
}