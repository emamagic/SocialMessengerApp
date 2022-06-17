package com.emamagic.conversation

import androidx.lifecycle.viewModelScope
import com.emamagic.base.base.BaseViewModel
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
): BaseViewModel<ConversationState, ConversationAction>() {

    init {
        viewModelScope.launch {
//            store.dispatch(ConversationAction.GetMyWorkspaces)
        }
    }

    override fun createInitialState(): ConversationState = ConversationState.initialize()

    override fun handleEvent(event: ConversationAction) {

    }

}