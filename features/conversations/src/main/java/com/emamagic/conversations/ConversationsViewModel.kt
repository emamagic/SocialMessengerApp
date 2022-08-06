package com.emamagic.conversations

import androidx.lifecycle.viewModelScope
import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.conversations.contract.ConversationsEvent
import com.emamagic.conversations.contract.ConversationsState
import com.emamagic.core.ResultWrapper
import com.emamagic.core.succeeded
import com.emamagic.core_android.ConversationType
import com.emamagic.core_android.ToastScope
import com.emamagic.domain.entities.ConversationEntity
import com.emamagic.domain.interactors.conversation.GetMyConversations
import com.emamagic.domain.interactors.workspace.GetCurrentWorkspace
import com.emamagic.mvi.BaseEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val currentWorkspace: GetCurrentWorkspace,
    private val myConversations: GetMyConversations
) : BaseViewModel<ConversationsState, ConversationsEvent, ConversationsRouter.Routes>() {

    private var currentList = mutableListOf<ConversationEntity>()
    private val filteredList = mutableListOf<ConversationWrapper>()

    init {
        viewModelScope.launch {
            setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
            flowOf(currentWorkspace(Unit)).flatMapLatest {
                if (it.succeeded) {
                    flowOf(myConversations(GetMyConversations.Params(it.data!!.id)))
                } else {
                    flowOf(ResultWrapper.Failed(it.error!!))
                }
            }.collectLatest { result ->
                if (result.succeeded && result.data!!.isNotEmpty()) {
                    currentList.clear()
                    currentList.addAll(result.data!!)
                    setState { copy(conversations = filter(currentList)) }
                }
            }
        }
    }

    fun toggleExpandability(item: ConversationWrapper.Header) {
        if (item.isExpanded) {
            val index = filteredList.indexOf(filteredList.find { it.idOrType == item.idOrType })
            currentList.addAll(index, item.items)
        } else {
            currentList.removeAll { it.type == item.idOrType && it.id != item.idOrType}
        }
        setState { copy(conversations = filter(currentList)) }
    }

    override fun createInitialState(): ConversationsState = ConversationsState.initialize()

    override fun handleEvent(event: ConversationsEvent) {

    }

    fun goToConversation(workspaceId: String, conversationId: String)  {
        viewModelScope.launch {
            routerDelegate.pushRoute(ConversationsRouter.Routes.ToChat(workspaceId, conversationId))
        }
    }

    private fun filter(conversations: List<ConversationEntity>): List<ConversationWrapper> {
        filteredList.clear()
        val pines = conversations.filter { item -> item.type == ConversationType.PINNED }
        val directs = conversations.filter { item -> item.type == ConversationType.DIRECT_MESSAGE }
        val groups =
            conversations.filter { item -> (item.type == ConversationType.PUBLIC_GROUP || item.type == ConversationType.PRIVATE_GROUP) }
        val bots = conversations.filter { item -> item.type == ConversationType.BOTS }

        val hPin = ConversationWrapper.Header(
            type = ConversationType.PINNED,
            displayNameId = R.string.pinned_groups,
            items = pines,
            isExpanded = true
        )
        val hDirect = ConversationWrapper.Header(
            type = ConversationType.DIRECT_MESSAGE,
            displayNameId = R.string.direct_messages,
            items = directs,
            isExpanded = true
        )
        val hGroup = ConversationWrapper.Header(
            type = ConversationType.PUBLIC_GROUP,
            displayNameId = R.string.public_groups,
            items = groups,
            isExpanded = true
        )
        val hBots = ConversationWrapper.Header(
            type = ConversationType.PINNED,
            displayNameId = R.string.bot_groups,
            items = bots,
            isExpanded = true
        )

        if (pines.isNotEmpty()) {
            filteredList.add(hPin)
            filteredList.addAll(pines.map { entity ->
                ConversationWrapper.Conversation(
                    entity.id,
                    entity
                )
            })
        }

        if (groups.isNotEmpty()) {
            filteredList.add(hGroup)
            filteredList.addAll(groups.map { entity ->
                ConversationWrapper.Conversation(
                    entity.id,
                    entity
                )
            })
        }

        if (directs.isNotEmpty()) {
            filteredList.add(hDirect)
            filteredList.addAll(directs.map { entity ->
                ConversationWrapper.Conversation(
                    entity.id,
                    entity
                )
            })
        }

        if (bots.isNotEmpty()) {
            filteredList.add(hBots)
            filteredList.addAll(bots.map { entity ->
                ConversationWrapper.Conversation(
                    entity.id,
                    entity
                )
            })
        }

        return filteredList.toImmutableList()
    }


}