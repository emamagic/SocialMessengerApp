package com.emamagic.conversation.contract

import com.emamagic.application.base.Action
import com.emamagic.entity.Workspace

sealed class ConversationAction: Action {

    object GetMyWorkspaces: ConversationAction()
    data class MyWorkspacesLoaded(val workspaces: List<Workspace>): ConversationAction()

}