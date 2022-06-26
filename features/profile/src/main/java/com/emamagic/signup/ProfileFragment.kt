package com.emamagic.signup

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.signup.contract.ProfileAction
import com.emamagic.signup.contract.ProfileState
import com.emamagic.signup.databinding.FragmentSignupBinding

class ProfileFragment: BaseFragment<FragmentSignupBinding, ProfileState, ProfileAction, ProfileRouter.Routes, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.signup_graph)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: ProfileState) {

    }


}