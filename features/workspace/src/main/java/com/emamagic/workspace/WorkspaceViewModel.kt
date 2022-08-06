package com.emamagic.workspace

import android.net.Uri
import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.core.succeeded
import com.emamagic.core_android.ToastScope
import com.emamagic.domain.interactors.UploadFile
import com.emamagic.domain.interactors.workspace.CreateWorkspace
import com.emamagic.domain.interactors.workspace.GetCurrentWorkspace
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.WorkspaceEffect
import com.emamagic.workspace.contract.WorkspaceEvent
import com.emamagic.workspace.contract.WorkspaceRouter
import com.emamagic.workspace.contract.WorkspaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkspaceViewModel @Inject constructor(
    private val getCurrentWorkspace: GetCurrentWorkspace,
    private val uploadFile: UploadFile,
    private val createWorkspace: CreateWorkspace
) : BaseViewModel<WorkspaceState, WorkspaceEvent, WorkspaceRouter.Routes>() {

    private lateinit var avatarHash: String

    override fun createInitialState(): WorkspaceState = WorkspaceState.initialize()

    init {
//        setEffect { BaseEffect.ShowLoading(scope = ToastScope.MODULE_SCOPE) }
//        withLoadingScope {
//            val currentWorkspace = getCurrentWorkspace(Unit)
//            if (currentWorkspace.succeeded) {
//                if (currentWorkspace.data!!.organizationId.isNullOrEmpty()) {
//                    setEffect { WorkspaceEffect.Init(false) }
//                } else {
//                    setEffect { WorkspaceEffect.Init(true) }
//                }
//            }
//            setEffect { WorkspaceEffect.Init(false) }
//            setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
//        }
    }

    override fun handleEvent(event: WorkspaceEvent) {
        when (event) {
            is WorkspaceEvent.UserPickedAvatar -> uploadWorkspaceAvatar(event.uri)
            is WorkspaceEvent.CreateDefaultWorkspace -> createDefaultWorkspace(event.workspaceName)
        }
    }

    private fun uploadWorkspaceAvatar(uri: Uri) = withLoadingScope {
        uploadFile(uri.toString()).collect {
            if (it.succeeded) {
                val attachment = it.data!![0]
                avatarHash = attachment.hash
                setState { copy(avatarUrl = attachment.url) }
            }
        }
    }

    private fun createDefaultWorkspace(workspaceName: String) = withLoadingScope {
        createWorkspace(CreateWorkspace.Params(workspaceName, avatarHash)).manageResult(success = {

        })
    }

}