package com.emamagic.workspace

import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.core.succeeded
import com.emamagic.core_android.ToastScope
import com.emamagic.domain.interactors.workspace.GetCurrentWorkspace
import com.emamagic.mvi.BaseEffect
import com.emamagic.workspace.contract.WorkspaceEvent
import com.emamagic.workspace.contract.WorkspaceRouter
import com.emamagic.workspace.contract.WorkspaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkspaceViewModel @Inject constructor(
    private val getCurrentWorkspace: GetCurrentWorkspace
) : BaseViewModel<WorkspaceState, WorkspaceEvent, WorkspaceRouter.Routes>() {

    override fun createInitialState(): WorkspaceState = WorkspaceState.initialize()

    init {
        setEffect { BaseEffect.ShowLoading(scope = ToastScope.MODULE_SCOPE) }
        withLoadingScope {
            val currentWorkspace = getCurrentWorkspace()
            if (currentWorkspace.succeeded) {
                if (!currentWorkspace.data!!.organizationId.isNullOrEmpty()) {
                    setState { copy(canCreateOrgWorkspace = true) }
                }
            }
            setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
        }
    }

    override fun handleEvent(event: WorkspaceEvent) {

    }


}