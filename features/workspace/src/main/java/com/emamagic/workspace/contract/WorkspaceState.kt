package com.emamagic.workspace.contract

import com.emamagic.mvi.State

data class WorkspaceState(
    val canCreateOrgWorkspace: Boolean
): State {
    companion object {
        fun initialize() = WorkspaceState(canCreateOrgWorkspace = false)
    }
}