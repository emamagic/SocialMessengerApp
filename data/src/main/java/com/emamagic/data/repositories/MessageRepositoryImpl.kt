package com.emamagic.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emamagic.core.AuthUserScope
import com.emamagic.data.db.DatabaseTransactionRunner
import com.emamagic.data.db.dao.MessageDao
import com.emamagic.data.db.dao.MessageRemoteKeysDao
import com.emamagic.data.network.RestProvider
import com.emamagic.domain.entities.MessageEntity
import com.emamagic.domain.interactors.GetMessages
import com.emamagic.domain.repositories.MessageRepository
import com.emamagic.safe.SafeApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@AuthUserScope
class MessageRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    private val messageDao: MessageDao,
    private val messageRemoteKeysDao: MessageRemoteKeysDao,
    private val transactionRunner: DatabaseTransactionRunner
): SafeApi(), MessageRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getMessages(params: GetMessages.Params): Flow<PagingData<MessageEntity>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = MessageRemoteMediator(
            transactionRunner,
            messageDao,
            messageRemoteKeysDao,
            restProvider.messageService,
            params.workspaceId,
            params.conversationId,
            params.lastViewAt
        ),
        pagingSourceFactory = { messageDao.loadMessages(params.conversationId) }
    ).flow


}