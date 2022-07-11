package com.emamagic.workspace.contract

import com.emamagic.mvi.State

data class WorkspaceState(
    val avatarUrl: String?
) : State {
    companion object {
        fun initialize() = WorkspaceState(
            avatarUrl = null
        )
    }
}