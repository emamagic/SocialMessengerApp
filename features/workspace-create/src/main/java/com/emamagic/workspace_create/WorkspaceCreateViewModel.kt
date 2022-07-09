package com.emamagic.workspace_create

import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.workspace_create.contract.WorkspaceCreateEvent
import com.emamagic.workspace_create.contract.WorkspaceCreateRouter
import com.emamagic.workspace_create.contract.WorkspaceCreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkspaceCreateViewModel @Inject constructor(

) : BaseViewModel<WorkspaceCreateState, WorkspaceCreateEvent, WorkspaceCreateRouter.Routes>() {


    override fun createInitialState(): WorkspaceCreateState = WorkspaceCreateState.initialize()

    override fun handleEvent(event: WorkspaceCreateEvent) {

    }
}