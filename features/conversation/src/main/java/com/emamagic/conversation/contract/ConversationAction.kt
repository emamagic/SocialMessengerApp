package com.emamagic.conversation.contract

import com.emamagic.mvi.Action
import com.emamagic.entity.Workspace

sealed class ConversationAction: com.emamagic.mvi.Action {

    object GetMyWorkspaces: ConversationAction()
    data class MyWorkspacesLoaded(val workspaces: List<Workspace>): ConversationAction()

}