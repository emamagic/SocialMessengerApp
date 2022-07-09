package com.emamagic.workspace_create

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.workspace_create.contract.WorkspaceCreateEvent
import com.emamagic.workspace_create.contract.WorkspaceCreateRouter
import com.emamagic.workspace_create.contract.WorkspaceCreateState
import com.emamagic.workspace_create.databinding.FragmentWorkspaceCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkspaceCreateFragment: BaseFragment<FragmentWorkspaceCreateBinding, WorkspaceCreateState, WorkspaceCreateEvent, WorkspaceCreateRouter.Routes, WorkspaceCreateViewModel>() {

    override val viewModel: WorkspaceCreateViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.workspace_create_graph)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: WorkspaceCreateState) {

    }
}