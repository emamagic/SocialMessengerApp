package com.emamagic.conversations.contract

import com.emamagic.domain.entities.Workspace
import com.emamagic.mvi.EVENT

sealed class ConversationsEvent: EVENT {

    object GetMyWorkspaces: ConversationsEvent()
    data class MyWorkspacesLoaded(val workspaces: List<Workspace>): ConversationsEvent()

}