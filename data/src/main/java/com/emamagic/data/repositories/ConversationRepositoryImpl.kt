package com.emamagic.data.repositories

import com.dropbox.android.external.store4.*
import com.emamagic.core.AuthUserScope
import com.emamagic.core.Error
import com.emamagic.core.ResultWrapper
import com.emamagic.data.db.dao.ConversationDao
import com.emamagic.data.network.RestProvider
import com.emamagic.data.toResponse
import com.emamagic.data.toResult
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.interactors.conversation.GetMyConversations
import com.emamagic.domain.repositories.ConversationRepository
import com.emamagic.safe.SafeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AuthUserScope
class ConversationRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    private val conversationDao: ConversationDao
): SafeApi(), ConversationRepository {

    private val store: Store<String, List<ConversationEntity>> = StoreBuilder.from(
        fetcher = Fetcher.of { workspaceId: String -> restProvider.conversationService.getConversations(workspaceId) },
        sourceOfTruth = SourceOfTruth.of(
            reader = conversationDao::loadConversations,
            writer = { _: String, entities -> conversationDao.insert(entities) },
            delete = conversationDao::deleteById,
            deleteAll = conversationDao::deleteAll
        )).build()

    override suspend fun getCurrentConversation(): ResultWrapper<ConversationEntity> {
        TODO()
    }

//    override suspend fun getMyConversations(params: GetMyConversations.Params): ResultWrapper<List<ConversationEntity>> = fresh {
//        restProvider.conversationService.getConversations(params.workspaceId).toResponse()
//    }.toResult()

    override fun getMyConversations(params: GetMyConversations.Params): Flow<ResultWrapper<List<ConversationEntity>>> =
        store.stream(StoreRequest.cached(key = params.workspaceId, refresh = true)).map {
            when (it) {
                is StoreResponse.Loading -> ResultWrapper.FetchLoading(null)
                is StoreResponse.Error -> ResultWrapper.Failed(Error(message = it.errorMessageOrNull()))
                is StoreResponse.NoNewData -> ResultWrapper.Success(emptyList())
                is StoreResponse.Data -> ResultWrapper.Success(it.value)
            }
        }

}