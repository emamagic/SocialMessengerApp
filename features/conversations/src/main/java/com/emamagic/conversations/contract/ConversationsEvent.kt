package com.emamagic.conversations.contract

import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.mvi.EVENT

sealed class ConversationsEvent: EVENT {

    object GetMyWorkspaces: ConversationsEvent()
    data class MyWorkspacesLoaded(val workspaceEntities: List<WorkspaceEntity>): ConversationsEvent()

}