package com.emamagic.conversations

import androidx.lifecycle.viewModelScope
import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.conversations.contract.ConversationsEvent
import com.emamagic.conversations.contract.ConversationsState
import com.emamagic.core.Error
import com.emamagic.core_android.ToastScope
import com.emamagic.mvi.BaseEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
): BaseViewModel<ConversationsState, ConversationsEvent, ConversationsRouter.Routes>() {

    init {
        viewModelScope.launch {
            setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
//            store.dispatch(ConversationAction.GetMyWorkspaces)
//            delay(5000)
//            onError(Error(statusCode = 401))
        }
    }

    override fun createInitialState(): ConversationsState = ConversationsState.initialize()

    override fun handleEvent(event: ConversationsEvent) {

    }

}