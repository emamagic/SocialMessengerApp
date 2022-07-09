package com.emamagic.workspace_create.contract

import com.emamagic.mvi.State

data class WorkspaceCreateState(
    val test: String
): State {
    companion object {
        fun initialize() = WorkspaceCreateState(test = "test")
    }
}