package com.emamagic.workspace.contract

import android.net.Uri
import com.emamagic.mvi.EVENT

sealed class WorkspaceEvent: EVENT {

    data class UserPickedAvatar(val uri: Uri): WorkspaceEvent()
    data class CreateDefaultWorkspace(val workspaceName: String): WorkspaceEvent()


}