package com.emamagic.conversation.contract

import com.emamagic.domain.entities.Workspace

sealed class ConversationAction: com.emamagic.mvi.EVENT {

    object GetMyWorkspaces: ConversationAction()
    data class MyWorkspacesLoaded(val workspaces: List<Workspace>): ConversationAction()

}