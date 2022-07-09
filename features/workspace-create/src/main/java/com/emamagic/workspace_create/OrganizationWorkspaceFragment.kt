package com.emamagic.workspace_create

import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationWorkspaceFragment: Fragment() {

    val viewModel: WorkspaceCreateViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.workspace_create_graph)
}