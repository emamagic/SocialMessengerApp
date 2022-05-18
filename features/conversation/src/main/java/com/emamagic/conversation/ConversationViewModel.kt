package com.emamagic.conversation

import androidx.lifecycle.viewModelScope
import com.emamagic.base.base.BaseViewModel
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState
import com.emamagic.conversation.contract.redux.ConversationStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val store: ConversationStore
): BaseViewModel<ConversationState, ConversationAction>(store) {

    init {
        viewModelScope.launch {
            store.dispatch(ConversationAction.GetMyWorkspaces)
        }
    }

}