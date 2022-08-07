package com.emamagic.data.repositories

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.emamagic.data.db.DatabaseTransactionRunner
import com.emamagic.data.db.dao.MessageDao
import com.emamagic.data.db.dao.MessageRemoteKeysDao
import com.emamagic.data.network.services.MessageService
import com.emamagic.domain.entities.MessageEntity
import com.emamagic.domain.entities.MessageRemoteKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator(
    private val transactionRunner: DatabaseTransactionRunner,
    private val messageDao: MessageDao,
    private val messageRemoteDao: MessageRemoteKeysDao,
    private val messageService: MessageService,
    private val workspaceId: String,
    private val conversationId: String,
    private val lastViewAt: Long
) : RemoteMediator<Int, MessageEntity>()  {

    private val initialPage = 1

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MessageEntity>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                Log.e("TAG", "loadMessages: REFRESH", )
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: initialPage
            }
            LoadType.PREPEND -> {
                Log.e("TAG", "loadMessages: PREPEND", )
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                Log.e("TAG", "loadMessages: APPEND", )
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = flowOf(messageService.getPageBeforeMessage(
                workspaceId,
                conversationId,
                page.toString(),
                state.config.pageSize.toString(),
                lastViewAt
            )).zip(flowOf(messageService.getPageAfterMessage(
                workspaceId,
                conversationId,
                page.toString(),
                state.config.pageSize.toString(),
                lastViewAt
            ))) { fist , second ->
                val messages = mutableListOf<MessageEntity>()
                messages.addAll(fist)
                messages.addAll(second)
                messages
            }

            val messages = apiResponse.first()
            val endOfPaginationReached = messages.isEmpty()
            transactionRunner {
                if (loadType == LoadType.REFRESH) {
                    messageRemoteDao.clearRemoteKeys()
                    messageDao.deleteAll()
                }
                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = messages.map {
                    MessageRemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                messageRemoteDao.insert(keys)
                messageDao.insert(messages)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend  fun getRemoteKeyForLastItem(state: PagingState<Int, MessageEntity>): MessageRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { message ->
                // Get the remote keys of the last item retrieved
                messageRemoteDao.remoteKeysById(message.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MessageEntity>): MessageRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { message ->
                // Get the remote keys of the first items retrieved
                messageRemoteDao.remoteKeysById(message.id)
            }
    }

    private suspend  fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MessageEntity>
    ): MessageRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                messageRemoteDao.remoteKeysById(repoId)
            }
        }
    }
}