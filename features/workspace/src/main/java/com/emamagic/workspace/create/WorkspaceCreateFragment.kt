package com.emamagic.workspace.create

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import coil.load
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.common_ui.base.BasePagerAdapter
import com.emamagic.core_android.onTabSelected
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.WorkspaceEffect
import com.emamagic.workspace.WorkspaceViewModel
import com.emamagic.workspace.contract.WorkspaceEvent
import com.emamagic.workspace.contract.WorkspaceRouter
import com.emamagic.workspace.contract.WorkspaceState
import com.emamagic.workspace.databinding.FragmentWorkspaceCreateBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkspaceCreateFragment: BaseFragment<FragmentWorkspaceCreateBinding, WorkspaceState, WorkspaceEvent, WorkspaceRouter.Routes, WorkspaceViewModel>() {

    override val viewModel: WorkspaceViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.workspace_create_graph)

    private lateinit var pagerAdapter: BasePagerAdapter
    private lateinit var defaultWorkspaceFragment: DefaultWorkspaceFragment
    private lateinit var organizationWorkspaceFragment: OrganizationWorkspaceFragment
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val fragments = mutableListOf<Fragment>()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.tabLayout.onTabSelected { position ->
            if (position == 0) {
                binding.btnSubmit.text = "ایجاد فضای کاری آزاد"
            } else {
                binding.btnSubmit.text = "درخواست ایجاد فضای کاری تحت سازمان"
            }
        }

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {}
                1 -> {}
            }
        }
    }

    override fun renderViewState(viewState: WorkspaceState) {

    }

    override fun renderCustomViewEffect(viewEffect: BaseEffect): Boolean {
        when (viewEffect) {
            is WorkspaceEffect.Init -> {
                if (viewEffect.canCreateOrgWorkspace) {
                    organizationWorkspaceFragment = OrganizationWorkspaceFragment()
                }
                defaultWorkspaceFragment = DefaultWorkspaceFragment()
                fragments.add(defaultWorkspaceFragment)

                if (::organizationWorkspaceFragment.isInitialized) {
                    fragments.add(organizationWorkspaceFragment)
                }
                pagerAdapter = BasePagerAdapter(
                    fragments,
                    childFragmentManager,
                    viewLifecycleOwner.lifecycle
                )
                binding.viewPager.adapter = pagerAdapter
                tabLayoutMediator.attach()
            }
            else -> {}
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::tabLayoutMediator.isInitialized) {
            tabLayoutMediator.detach()
        }
    }
}