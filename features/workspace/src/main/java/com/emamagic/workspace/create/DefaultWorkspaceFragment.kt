package com.emamagic.workspace.create

import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.workspace.WorkspaceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefaultWorkspaceFragment: Fragment() {

    val viewModel: WorkspaceViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.workspace_create_graph)

}