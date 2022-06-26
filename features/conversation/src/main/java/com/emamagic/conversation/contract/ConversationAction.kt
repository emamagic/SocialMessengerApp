package com.emamagic.conversation.contract

import com.emamagic.domain.entities.Workspace
import com.emamagic.mvi.EVENT

sealed class ConversationAction: EVENT {

    object GetMyWorkspaces: ConversationAction()
    data class MyWorkspacesLoaded(val workspaces: List<Workspace>): ConversationAction()

}